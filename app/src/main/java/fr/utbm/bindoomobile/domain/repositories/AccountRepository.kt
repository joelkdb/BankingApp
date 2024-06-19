package fr.utbm.bindoomobile.domain.repositories

import fr.utbm.bindoomobile.data.datasource.local.entities.AccountEntity
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun getMainAccount(): AccountEntity
    suspend fun getAccountById(accountId: String): AccountEntity
    suspend fun getAccounts(): List<AccountEntity>
    fun getBalanceFlow(): Flow<MoneyAmount>

    suspend fun getRecipient(recipientAccount:String): String
    suspend fun getCardBalanceFlow(cardId: String): Flow<MoneyAmount>
}