package fr.utbm.bindoomobile.domain.usecases.account

import fr.utbm.bindoomobile.data.datasource.local.entities.AccountEntity
import fr.utbm.bindoomobile.domain.repositories.AccountRepository

class GetRecipientByAccountUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend fun execute(account: String): String {
        return accountRepository.getRecipient(account)
    }
}