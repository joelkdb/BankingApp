package fr.utbm.bindoomobile.domain.usecases.account

import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow

class GetCardBalanceObservableUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(cardId: String): Flow<MoneyAmount> {
        return accountRepository.getCardBalanceFlow(cardId)
    }
}