package fr.utbm.bindoomobile.domain.usecases.validation

import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.models.ValidationResult

class ValidateLoginUseCase {
    fun execute(login: String): ValidationResult {
        return if (isLoginValid(login)) {
            ValidationResult(true, null)
        } else if (login.isBlank()) {
            ValidationResult(false, ErrorType.FIELD_IS_EMPTY)
        } else {
            ValidationResult(false, ErrorType.INVALID_LOGIN_FIELD)
        }
    }

    private fun isLoginValid(login: String): Boolean {
        if (login.length < 3) return false
//        if (login.firstOrNull { it.isDigit() } == null) return false
//        if (login.firstOrNull { it.isLetter() } == null) return false

        return true
    }
}