package fr.utbm.bindoomobile.domain.usecases.validation

import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.models.ValidationResult

class ValidatePasswordUseCase {
    fun execute(password: String): ValidationResult {
        return if (isPasswordValid(password)) {
            ValidationResult(true, null)
        } else if (password.isBlank()) {
            ValidationResult(false, ErrorType.FIELD_IS_EMPTY)
        } else {
            ValidationResult(false, ErrorType.INVALID_PASSWORD_FIELD)
        }
    }

    private fun isPasswordValid(password: String): Boolean {
        if (password.length < 6) return false
        if (password.firstOrNull { it.isDigit() } == null) return false
        if (password.firstOrNull { it.isLetter() } == null) return false

        return true
    }
}