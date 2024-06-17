package fr.utbm.bindoomobile.domain.usecases.validation

import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.models.ValidationResult

class ValidateBillingAddressUseCase {
    fun execute(
        addressFirstLine: String,
        addressSecondLine: String
    ): ValidationResult {
        // Check only first line
        return if (addressFirstLine.isBlank()) {
            ValidationResult(true, validationError = ErrorType.FIELD_IS_EMPTY)
        } else {
            ValidationResult(isValid = true, validationError = null)
        }
    }
}