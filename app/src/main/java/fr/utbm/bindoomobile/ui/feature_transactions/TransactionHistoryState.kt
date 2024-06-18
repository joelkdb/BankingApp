package fr.utbm.bindoomobile.ui.feature_transactions

import androidx.paging.PagingData
import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_transactions.model.TransactionUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow

data class TransactionHistoryState(
    val transactionsPagingState: Flow<PagingData<TransactionUi>> = MutableStateFlow(PagingData.empty()),
    val screenError: UiText? = null
)
