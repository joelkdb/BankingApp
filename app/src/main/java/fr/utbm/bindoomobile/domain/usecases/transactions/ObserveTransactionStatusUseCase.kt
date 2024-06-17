package fr.utbm.bindoomobile.domain.usecases.transactions

import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionStatus
import fr.utbm.bindoomobile.domain.repositories.TransactionRepository
import kotlinx.coroutines.flow.Flow

class ObserveTransactionStatusUseCase(
    private val transactionRepository: TransactionRepository
) {
    fun execute(transactionId: Long): Flow<TransactionStatus> {
        return transactionRepository.getTransactionStatusFlow(transactionId)
    }
}