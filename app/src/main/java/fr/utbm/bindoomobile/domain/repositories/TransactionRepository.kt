package fr.utbm.bindoomobile.domain.repositories

import androidx.paging.PagingData
import fr.utbm.bindoomobile.domain.models.feature_transactions.Transaction
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionRowPayload
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionStatus
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionType
import kotlinx.coroutines.flow.Flow

interface TransactionRepository {
    suspend fun getTransactions(filterByType: TransactionType?): Flow<PagingData<Transaction>>
    fun getTransactionStatusFlow(transactionId: Long): Flow<TransactionStatus>
    suspend fun submitTransaction(payload: TransactionRowPayload)
}