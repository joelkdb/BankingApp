package fr.utbm.bindoomobile.domain.usecases.account

import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.domain.repositories.AccountRepository
import kotlinx.coroutines.flow.Flow

class GetTotalAccountBalanceUseCase(
    private val accountRepository: AccountRepository
) {
    fun execute(): Flow<MoneyAmount> {
        return accountRepository.getBalanceFlow()
    }
}