package fr.utbm.bindoomobile.ui.app_host

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.core.OperationResult
import fr.utbm.bindoomobile.domain.usecases.feature_app_lock.CheckAppLockUseCase
import fr.utbm.bindoomobile.domain.usecases.login.CheckIfLoggedInUseCase
import fr.utbm.bindoomobile.domain.usecases.onboarding.CheckIfPassedOnboardingUseCase
import fr.utbm.bindoomobile.ui.app_host.navigation.model.ConditionalNavigation
import fr.utbm.bindoomobile.ui.core.error.asUiTextError
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class AppViewModel(
    private val checkIfLoggedInUseCase: CheckIfLoggedInUseCase,
    private val checkIfPassedOnboardingUseCase: CheckIfPassedOnboardingUseCase,
    private val checkAppLockUseCase: CheckAppLockUseCase
) : ViewModel() {

    private val _appState: MutableStateFlow<AppState> = MutableStateFlow(AppState.Loading)
    val appState = _appState.asStateFlow()

    // This is a global app's viewModel
    init {
        emitIntent(AppIntent.EnterApp)
    }

    fun emitIntent(intent: AppIntent) {
        when (intent) {
            AppIntent.EnterApp -> {
                reduceAppLoading()
                reduceAppReadyCheck()
            }

            AppIntent.TryPostUnlock -> {
                val currState = _appState.value

                if (currState is AppState.Ready) {
                    _appState.update {
                        currState.copy(
                            requireUnlock = false
                        )
                    }
                }
            }

            is AppIntent.AppLockLogout -> {
                reduceAppReady(
                    appLocked = false,
                    conditionalNavigation = ConditionalNavigation(
                        requireLogin = true,
                        requireOnboarding = false,
                        requireCreateAppLock = false
                    )
                )
            }
        }
    }

    private fun reduceAppLoading() {
        _appState.update {
            AppState.Loading
        }
    }

    private fun reduceAppReadyCheck() {
        viewModelScope.launch {

            val isLoggedIn = OperationResult.runWrapped {
                checkIfLoggedInUseCase.execute()
            }

            when (isLoggedIn) {
                is OperationResult.Success -> {

                    val hasPassedOnboarding = checkIfPassedOnboardingUseCase.execute()
                    val appLocked = checkAppLockUseCase.execute()

                    reduceAppReady(
                        conditionalNavigation = ConditionalNavigation(
                            // Require create applock if closed this step on registration
                            requireCreateAppLock = !appLocked && isLoggedIn.data,
                            requireLogin = !isLoggedIn.data,
                            requireOnboarding = !hasPassedOnboarding
                        ),
                        appLocked = appLocked
                    )
                }

                is OperationResult.Failure -> {
                    reduceError(isLoggedIn.error.errorType)
                }
            }
        }
    }

    private fun reduceAppReady(
        conditionalNavigation: ConditionalNavigation,
        appLocked: Boolean
    ) {
        _appState.value = AppState.Ready(
            conditionalNavigation = conditionalNavigation,
            requireUnlock = appLocked
        )
    }

    private fun reduceError(errorType: ErrorType) {
        _appState.value = AppState.InitFailure(errorType.asUiTextError())
    }
}