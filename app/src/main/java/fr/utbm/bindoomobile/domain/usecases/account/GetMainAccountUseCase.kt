package fr.utbm.bindoomobile.domain.usecases.account

import fr.utbm.bindoomobile.data.datasource.local.entities.AccountEntity
import fr.utbm.bindoomobile.domain.repositories.AccountRepository

class GetMainAccountUseCase(
    private val accountRepository: AccountRepository,
) {
    suspend fun execute(): AccountEntity {
        return accountRepository.getMainAccount()
    }
}