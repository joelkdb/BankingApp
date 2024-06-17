package fr.utbm.bindoomobile.ui.feature_login

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import de.palm.composestateevents.consumed
import de.palm.composestateevents.triggered
import fr.utbm.bindoomobile.BuildConfig
import fr.utbm.bindoomobile.domain.core.AppError
import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.core.OperationResult
import fr.utbm.bindoomobile.domain.usecases.login.LoginUseCase
import fr.utbm.bindoomobile.domain.usecases.validation.ValidateLoginUseCase
import fr.utbm.bindoomobile.domain.usecases.validation.ValidatePasswordUseCase
import fr.utbm.bindoomobile.ui.core.error.asUiTextError
import fr.utbm.bindoomobile.ui.feature_cards.screen_add_card.UiField
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

class LoginViewModel(
    private val loginUseCase: LoginUseCase,
    private val validatePasswordUseCase: ValidatePasswordUseCase,
    private val validateLoginUseCase: ValidateLoginUseCase
) : ViewModel() {
    private val _loginState = MutableStateFlow(LoginScreenState())
    val loginState = _loginState.asStateFlow()

    init {
        if (BuildConfig.DEBUG) {
            _loginState.value = LoginScreenState.mock()
        }
    }

    fun emitIntent(intent: LoginIntent) {
        val currentState = _loginState.value

        when (intent) {
            is LoginIntent.EnterScreen -> {

            }

            is LoginIntent.LoginFieldChanged -> {
                when (intent.fieldType) {
                    LoginFieldType.USERNAME -> reduceFields(
                        currentState.formFields.copy(
                            loginField = UiField(
                                intent.fieldValue
                            )
                        )
                    )

                    LoginFieldType.PASSWORD -> reduceFields(
                        currentState.formFields.copy(
                            passwordField = UiField(intent.fieldValue)
                        )
                    )
                }
            }

            is LoginIntent.SubmitForm -> {
                val login = currentState.formFields.loginField.value
                val password = currentState.formFields.passwordField.value

                var formValidFlag = true

                val loginValidation = validateLoginUseCase.execute(login)
                val passwordValidation = validatePasswordUseCase.execute(password)

                if (!loginValidation.isValid) {
                    reduceFieldError(LoginFieldType.USERNAME, loginValidation.validationError)
                    formValidFlag = false
                }

                if (!passwordValidation.isValid) {
                    reduceFieldError(LoginFieldType.PASSWORD, passwordValidation.validationError)
                    formValidFlag = false
                }

                if (!formValidFlag) {
                    reduceError(ErrorType.GENERIC_VALIDATION_ERROR)
                } else {
                    reduceTryLogin(login = login, password = password)
                }
            }
        }
    }

    private fun reduceFields(fields: LoginFormFields) {
        _loginState.update { current ->
            current.copy(formFields = fields)
        }
    }

    private fun reduceFieldError(
        fieldType: LoginFieldType,
        errorType: ErrorType?
    ) {
        val currentFields = _loginState.value.formFields

        if (errorType != null) {
            val updatedFields = when (fieldType) {
                LoginFieldType.USERNAME -> {
                    currentFields.copy(loginField = currentFields.loginField.copy(error = errorType.asUiTextError()))
                }

                LoginFieldType.PASSWORD -> {
                    currentFields.copy(passwordField = currentFields.passwordField.copy(error = errorType.asUiTextError()))
                }
            }

            _loginState.update { currentState ->
                currentState.copy(formFields = updatedFields)
            }
        }
    }

    private fun reduceError(error: ErrorType) {
        _loginState.update { curr ->
            curr.copy(
                isLoading = false,
                loginEvent = triggered(
                    OperationResult.Failure(error = AppError(error))
                )
            )
        }
    }

    private fun reduceTryLogin(login: String, password: String) {
        print("Entered in reduceTryLogin...")
        _loginState.update {
            it.copy(
                isLoading = true,
            )
        }

        viewModelScope.launch {
            val loginResult = OperationResult.runWrapped {
                loginUseCase.execute(login, password)
            }

            _loginState.update {
                it.copy(
                    isLoading = false,
                    loginEvent = triggered(loginResult)
                )
            }
        }
    }

    fun onLoginEventConsumed() {
        _loginState.update {
            it.copy(
                loginEvent = consumed()
            )
        }
    }
}