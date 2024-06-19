package fr.utbm.bindoomobile.domain.usecases.account

import fr.utbm.bindoomobile.data.datasource.local.entities.AccountEntity
import fr.utbm.bindoomobile.domain.models.feature_cards.PaymentCard
import fr.utbm.bindoomobile.domain.repositories.AccountRepository


class GetAccountByIdUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(accountId: String): AccountEntity {
        return accountRepository.getAccountById(accountId)
    }
}