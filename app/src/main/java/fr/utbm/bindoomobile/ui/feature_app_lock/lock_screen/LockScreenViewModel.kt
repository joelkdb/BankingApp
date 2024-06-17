package fr.utbm.bindoomobile.ui.feature_app_lock.lock_screen

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.domain.core.OperationResult
import fr.utbm.bindoomobile.domain.models.feature_app_lock.AuthenticationResult
import fr.utbm.bindoomobile.domain.models.feature_app_lock.BiometricsAvailability
import fr.utbm.bindoomobile.domain.usecases.feature_app_lock.AuthenticateWithPinUseCase
import fr.utbm.bindoomobile.domain.usecases.feature_app_lock.CheckAppLockedWithBiometricsUseCase
import fr.utbm.bindoomobile.domain.usecases.feature_app_lock.CheckIfBiometricsAvailableUseCase
import fr.utbm.bindoomobile.domain.usecases.login.LogoutUseCase
import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_app_lock.components.AppLockUiState
import fr.utbm.bindoomobile.ui.feature_app_lock.core.AppLockIntent
import fr.utbm.bindoomobile.ui.feature_app_lock.core.AppLockViewModel
import fr.utbm.bindoomobile.ui.feature_app_lock.core.BiometricsViewModel
import fr.utbm.bindoomobile.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import fr.utbm.bindoomobile.ui.feature_app_lock.core.biometrics.BiometricsIntent
import fr.utbm.bindoomobile.ui.feature_logout.LogoutIntent
import fr.utbm.bindoomobile.ui.feature_logout.LogoutViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LockScreenViewModel(
    private val authenticateWithPinUseCase: AuthenticateWithPinUseCase,
    private val checkIfBiometricsAvailableUseCase: CheckIfBiometricsAvailableUseCase,
    private val checkIfAppLockedWithBiometricsUseCase: CheckAppLockedWithBiometricsUseCase,
    private val logoutUseCase: LogoutUseCase,
) : ViewModel(),
    AppLockViewModel,
    BiometricsViewModel,
    LogoutViewModel
{

    private val _state = MutableStateFlow(
        LockScreenState(
            uiState = AppLockUiState(
                prompt = UiText.StringResource(R.string.unlock_app)
            ),
        )
    )

    val state = _state.asStateFlow()
    override val pinLength = 4

    override fun emitAppLockIntent(intent: AppLockIntent) {
        when (intent) {
            is AppLockIntent.PinFieldChange -> reduce(intent)
            is AppLockIntent.BiometricsBtnClicked -> {
                _state.update {
                    it.copy(showBiometricsPromptEvent = triggered)
                }
            }
        }
    }

    override fun emitBiometricsIntent(intent: BiometricsIntent) {
        when (intent) {
            is BiometricsIntent.ConsumeAuthResult -> {
                when (intent.result) {
                    is BiometricAuthResult.Success -> {
                        _state.update {
                            it.copy(
                                unlockWithBiometricsResultEvent = triggered(intent.result)
                            )
                        }
                    }

                    is BiometricAuthResult.Failure -> {
                        // Do nothing
                        // User can init biometrics auth again or use pin code
                    }
                }
            }

            is BiometricsIntent.RefreshBiometricsAvailability -> {
                val appLockedWithBiometrics = checkIfAppLockedWithBiometricsUseCase.execute()

                // Check if locked first, as it's faster
                if (!appLockedWithBiometrics) {
                    _state.update {
                        it.copy(uiState = it.uiState.copy(showBiometricsBtn = false))
                    }
                } else {
                    // Check biometrics still available
                    val biometricsAvailable = checkIfBiometricsAvailableUseCase.execute()
                    val showBiometricsBtn = biometricsAvailable == BiometricsAvailability.Available

                    _state.update {
                        it.copy(
                            uiState = it.uiState.copy(showBiometricsBtn = showBiometricsBtn),
                            showBiometricsPromptEvent = triggered
                        )
                    }
                }
            }
        }
    }

    private fun reduce(intent: AppLockIntent.PinFieldChange) {
        _state.update {
            it.copy(
                uiState = it.uiState.copy(
                    pinValue = intent.pin,
                    // Clear error on edit
                    error = null
                )
            )
        }

        if (intent.pin.length == pinLength) {
            _state.update {
                it.copy(
                    uiState = it.uiState.copy(
                        isLoading = true
                    )
                )
            }

            viewModelScope.launch {
                val pinRes = authenticateWithPinUseCase.execute(
                    pin = _state.value.uiState.pinValue
                )

                when (pinRes) {
                    is AuthenticationResult.Success -> {
                        reducePinSuccess()
                    }

                    is AuthenticationResult.Failure -> {
                        reducePinError(pinRes)
                    }
                }
            }
        }
    }

    override fun emitLogoutIntent(intent: LogoutIntent) {
        when (intent) {
            is LogoutIntent.ToggleLogoutDialog -> {
                _state.update {
                    it.copy(
                        logoutState = it.logoutState.copy(
                            showLogoutDialog = intent.isShown
                        )
                    )
                }
            }

            is LogoutIntent.ConfirmLogOut -> {
                _state.update {
                    it.copy(
                        logoutState = it.logoutState.copy(
                            isLoading = true
                        )
                    )
                }

                viewModelScope.launch {
                    val logoutRes = OperationResult.runWrapped {
                        logoutUseCase.execute()
                    }

                    _state.update {
                        it.copy(
                            logoutState = it.logoutState.copy(
                                logoutEvent = triggered(logoutRes)
                            )
                        )
                    }
                }
            }
        }
    }

    private fun reducePinSuccess() {
        _state.update {
            it.copy(
                unlockWithPinEvent = triggered,
                uiState = it.uiState.copy(
                    isLoading = false
                )
            )
        }
    }

    private fun reducePinError(pinRes: AuthenticationResult.Failure) {
        // TODO string template
        val postfix = if (pinRes.remainingAttempts != null) {
            " ${pinRes.remainingAttempts} attempts left"
        } else {
            ""
        }

        _state.update {
            it.copy(
                uiState = it.uiState.copy(
                    isLoading = false,
                    pinValue = "",
                    error = UiText.DynamicString("Invalid PIN.$postfix")
                )
            )
        }
    }

    fun consumeUnlockWithPinEvent() {
        _state.update {
            it.copy(
                unlockWithPinEvent = consumed
            )
        }
    }

    fun consumeShowBiometricsPromptEvent() {
        _state.update {
            it.copy(
                showBiometricsPromptEvent = consumed
            )
        }
    }

    fun consumeBiometricAuthEvent() {
        _state.update {
            it.copy(
                unlockWithBiometricsResultEvent = consumed()
            )
        }
    }

    fun consumeLogoutEvent() {
        _state.update {
            it.copy(
                logoutState = it.logoutState.copy(
                    logoutEvent = consumed()
                )
            )
        }
    }
}