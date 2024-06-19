package fr.utbm.bindoomobile.domain.usecases.account

import fr.utbm.bindoomobile.data.datasource.local.entities.AccountEntity
import fr.utbm.bindoomobile.domain.repositories.AccountRepository


class GetDefaultAccountUseCase(
    private val accountRepository: AccountRepository
) {
    suspend fun execute(): AccountEntity? {
        val accounts = accountRepository.getAccounts()

        return accounts.find {
            it.priority == 1
        } ?: accounts.firstOrNull()
    }
}