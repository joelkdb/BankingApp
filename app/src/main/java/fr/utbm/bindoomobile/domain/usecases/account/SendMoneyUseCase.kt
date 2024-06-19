package fr.utbm.bindoomobile.domain.usecases.account

import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionRowPayload
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionType
import fr.utbm.bindoomobile.domain.repositories.TransactionRepository

class SendMoneyUseCase(
    private val transactionRepository: TransactionRepository
) {
    suspend fun execute(
        amount: MoneyAmount,
        fromAccountId: String,
        recipientId: String,
        comment: String
    ) {
        return transactionRepository.submitTransaction(
            TransactionRowPayload(
                type = TransactionType.SEND,
                amount = amount,
                accountId = fromAccountId,
                destinationId = recipientId,
                comment = comment
            )
        )
    }
}