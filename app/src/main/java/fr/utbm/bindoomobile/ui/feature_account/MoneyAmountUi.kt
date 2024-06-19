package fr.utbm.bindoomobile.ui.feature_account

import fr.utbm.bindoomobile.domain.models.feature_account.BalanceCurrency
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import java.text.DecimalFormat
import java.text.DecimalFormatSymbols
import java.util.Locale

data class MoneyAmountUi(
    val amountStr: String,
) {
    companion object {
        fun mapFromDomain(balance: MoneyAmount): MoneyAmountUi {
            val symbols = DecimalFormatSymbols(Locale.getDefault())
            val decimalFormat = DecimalFormat("#,##0.##", symbols)
            decimalFormat.isGroupingUsed = false
            val formattedValue = decimalFormat.format(balance.value)


            // Add currency prefixes
            return when (balance.currency) {
                BalanceCurrency.XOF -> {
                    MoneyAmountUi("${formattedValue}F")
                }

                BalanceCurrency.USD -> {
                    MoneyAmountUi("$formattedValue$")
                }

                BalanceCurrency.EUR -> {
                    MoneyAmountUi("$formattedValue€")
                }
            }
        }

        fun mapFromDomainTwo(balance: MoneyAmount): MoneyAmountUi {
            val symbols = DecimalFormatSymbols(Locale.getDefault())
            val decimalFormat = DecimalFormat("#,##0.##", symbols)
            decimalFormat.isGroupingUsed = false
            val formattedValue = decimalFormat.format(balance.value)


            // Add currency prefixes
            return when (balance.currency) {
                BalanceCurrency.XOF -> {
                    MoneyAmountUi("$formattedValue XOF")
                }

                BalanceCurrency.USD -> {
                    MoneyAmountUi("$formattedValue USD")
                }

                BalanceCurrency.EUR -> {
                    MoneyAmountUi("$formattedValue EUR")
                }
            }
        }
    }
}
