package fr.utbm.bindoomobile.ui.feature_transactions.model

import fr.utbm.bindoomobile.domain.models.feature_transactions.Transaction
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionStatus
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionType
import fr.utbm.bindoomobile.ui.core.extensions.getFormattedDate
import fr.utbm.bindoomobile.ui.feature_account.MoneyAmountUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

data class TransactionUi(
    val id: Long,
    val type: TransactionType,
    val transactionLabel: String? = null,
    val recentStatus: TransactionStatus,
    val statusFlow: Flow<TransactionStatus>,
    val value: MoneyAmountUi,
    val transactionDate: String,
    val bankLogo: String? = null
) {
    companion object {
        fun mock(
            id: Long = 0,
            type: TransactionType = TransactionType.SEND,
            transactionLabel: String = "Transfert de A vers B avec une libellé de transactions très long par exemple",
            status: TransactionStatus = TransactionStatus.COMPLETED,
            transactionDate: String = "13 Oct 2021",
            value: MoneyAmountUi = MoneyAmountUi("200 XOF"),
        ): TransactionUi {
            return TransactionUi(
                id = id,
                type = type,
                transactionLabel = transactionLabel,
                transactionDate = transactionDate,
                recentStatus = status,
                statusFlow = flowOf(status),
                value = value
            )
        }

        fun mapFromDomain(
            transaction: Transaction,
            statusFlow: Flow<TransactionStatus>? = null
        ): TransactionUi {


            return TransactionUi(
                id = transaction.id,
                value = MoneyAmountUi.mapFromDomain(transaction.value),
                transactionLabel = transaction.label,
                transactionDate = transaction.createdDate.getFormattedDate("dd MMM yyyy HH:mm"),
                type = transaction.type,
                recentStatus = transaction.recentStatus,
                statusFlow = statusFlow ?: flowOf(transaction.recentStatus)
            )
        }
    }
}
