package fr.utbm.bindoomobile.domain.usecases.validation

import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.models.ValidationResult

class ValidateCardExpirationUseCase {
    fun execute(expirationTime: Long?): ValidationResult {
        return if (expirationTime == null) {
            ValidationResult(isValid = false, validationError = ErrorType.DATE_UNSPECIFIED)
        } else if (System.currentTimeMillis() > expirationTime) {
            ValidationResult(isValid = false, validationError = ErrorType.CARD_EXPIRED)
        } else {
            ValidationResult(isValid = true, validationError = null)
        }
    }
}