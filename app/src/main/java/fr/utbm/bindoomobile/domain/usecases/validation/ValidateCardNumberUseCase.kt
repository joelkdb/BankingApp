package fr.utbm.bindoomobile.domain.usecases.validation

import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.models.ValidationResult

class ValidateCardNumberUseCase {
    fun execute(cardNumber: String): ValidationResult {
        return if (cardNumber.isNotBlank() && validateWithLuhnAlgorithm(cardNumber)) {
            ValidationResult(true)
        } else {
            return if (cardNumber.isBlank()) {
                ValidationResult(false, ErrorType.FIELD_IS_EMPTY)
            }
            else {
                ValidationResult(false, ErrorType.INVALID_CARD_NUMBER)
            }
        }
    }

    private fun validateWithLuhnAlgorithm(cardNumber: String): Boolean {
        var checksum: Int = 0
        for (i in cardNumber.length - 1 downTo 0 step 2) {
            checksum += cardNumber[i] - '0'
        }
        for (i in cardNumber.length - 2 downTo 0 step 2) {
            val n: Int = (cardNumber[i] - '0') * 2
            checksum += if (n > 9) n - 9 else n
        }
        return checksum % 10 == 0
    }
}