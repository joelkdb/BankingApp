package fr.utbm.bindoomobile.domain.models.feature_account

// Wrapper class for money representation
// Used to encapsulation of chosen base types (Double, BigDecimal and so on)
data class MoneyAmount(
    val value: Float,
    val currency: BalanceCurrency = BalanceCurrency.XOF,
) {
    operator fun plus(other: MoneyAmount): MoneyAmount {
        return this.copy(value = this.value + other.value)
    }

    operator fun minus(other: MoneyAmount): MoneyAmount {
        return this.copy(value = this.value - other.value)
    }

    operator fun compareTo(other: MoneyAmount): Int {
        return this.value.compareTo(other.value)
    }
}