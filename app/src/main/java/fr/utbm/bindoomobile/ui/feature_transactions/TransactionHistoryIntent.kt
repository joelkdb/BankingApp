package fr.utbm.bindoomobile.ui.feature_transactions

import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionType


sealed class TransactionHistoryIntent {
    object InitLoad: TransactionHistoryIntent()
    data class ChangeTransactionFilter(val filterByType: TransactionType?): TransactionHistoryIntent()
}
