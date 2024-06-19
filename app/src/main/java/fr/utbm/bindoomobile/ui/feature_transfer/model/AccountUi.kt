package fr.utbm.bindoomobile.ui.feature_transfer.model

import androidx.compose.ui.graphics.Color
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.data.datasource.local.entities.AccountEntity
import fr.utbm.bindoomobile.domain.models.feature_account.AccountType
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.ui.core.extensions.splitStringWithDivider
import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_account.MoneyAmountUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class AccountUi(
    val id: String,
    val isPrimary: Boolean,
    val accountNumber: String,
    val recentBalance: String,
    val balanceFlow: Flow<String>,
    val accountType: UiText,
    val bankName: String
) {
    companion object {
        fun mock(
        ): AccountUi {
            val mockNumber = "2298126833989874"

            return AccountUi(
                id = mockNumber,
                accountNumber = mockNumber.splitStringWithDivider(),
                recentBalance = "749965 XOF",
                balanceFlow = flowOf("2887 F"),
                accountType = UiText.DynamicString("Classic"),
                isPrimary = true,
                bankName = "COOPEC-AD"
            )
        }

        fun mapFromDomain(
            account: AccountEntity,
            bankName: String,
            balanceFlow: Flow<String>? = null
        ): AccountUi {
            val recentBalance =
                MoneyAmountUi.mapFromDomainTwo(MoneyAmount(account.balance.toFloat())).amountStr

            return AccountUi(
                id = account.code,
                accountNumber = account.number,
                isPrimary = account.priority == 1,
                recentBalance = recentBalance,
                balanceFlow = balanceFlow ?: flowOf(recentBalance),
                accountType = when (account.accountTypeCode) {
                    AccountType.C.name -> UiText.StringResource(R.string.classic)
                    AccountType.T.name -> UiText.StringResource(R.string.tontine)
                    else -> UiText.StringResource(R.string.classic)
                },
                bankName = bankName
            )
        }
    }
}
