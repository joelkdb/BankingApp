package fr.utbm.bindoomobile.domain.models

import fr.utbm.bindoomobile.domain.core.ErrorType

data class ValidationResult(
    val isValid: Boolean,
    val validationError: ErrorType? = null
)
