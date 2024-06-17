package fr.utbm.bindoomobile.domain.repositories

import fr.utbm.bindoomobile.data.datasource.local.entities.AccountEntity
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import kotlinx.coroutines.flow.Flow

interface AccountRepository {

    suspend fun getMainAccount(): AccountEntity
    fun getBalanceFlow(): Flow<MoneyAmount>
    suspend fun getCardBalanceFlow(cardId: String): Flow<MoneyAmount>
    suspend fun topUpCard(cardId: String, amount: MoneyAmount)
    suspend fun sendFromCard(cardId: String, amount: MoneyAmount, contactId: Long)
}