package fr.utbm.bindoomobile.domain.usecases.transactions

import androidx.paging.PagingData
import fr.utbm.bindoomobile.domain.models.feature_transactions.Transaction
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionType
import fr.utbm.bindoomobile.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow

class GetTransactionsUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(filterByType: TransactionType?): Flow<PagingData<Transaction>> {
        return transactionRepository.getTransactions(filterByType)
    }
}