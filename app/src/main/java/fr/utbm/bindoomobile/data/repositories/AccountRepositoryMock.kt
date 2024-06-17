package fr.utbm.bindoomobile.data.repositories

import com.cioccarellia.ksprefs.KsPrefs
import fr.utbm.bindoomobile.data.app.PrefKeys
import fr.utbm.bindoomobile.data.datasource.local.dao.AccountDao
import fr.utbm.bindoomobile.data.datasource.local.dao.AgencyDao
import fr.utbm.bindoomobile.data.datasource.local.dao.CardsDao
import fr.utbm.bindoomobile.data.datasource.local.entities.AccountEntity
import fr.utbm.bindoomobile.data.datasource.remote.api.ClientApiService
import fr.utbm.bindoomobile.domain.core.AppError
import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.domain.repositories.AccountRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AccountRepositoryMock(
    private val coroutineDispatcher: CoroutineDispatcher,
    private val cardsDao: CardsDao,
    private val accountsDao: AccountDao,
    private val agencyDao: AgencyDao,
    private val prefs: KsPrefs,
    private val api: ClientApiService
) : AccountRepository {
    override suspend fun getMainAccount(): AccountEntity {
        return accountsDao.getAccountFilteredByPriority()!!.first()
    }

    override fun getBalanceFlow(): Flow<MoneyAmount> {
        return flow {
            while (true) {
                emit(calculateBalance())
                delay(MOCK_OBSERVING_DELAY)
            }
        }.flowOn(coroutineDispatcher)
    }

    override suspend fun getCardBalanceFlow(cardId: String): Flow<MoneyAmount> {
        // Check card exists
        cardsDao.getCardByNumber(cardId) ?: throw AppError(ErrorType.CARD_NOT_FOUND)

        return flow {
            while (true) {
                // For mock app emit last card balance saved in db
                val card = cardsDao.getCardByNumber(cardId)
                    ?: throw AppError(ErrorType.CARD_HAS_BEEN_DELETED)
                emit(MoneyAmount(card.recentBalance))

                delay(MOCK_OBSERVING_DELAY)
            }
        }.flowOn(coroutineDispatcher)
    }

    override suspend fun topUpCard(cardId: String, amount: MoneyAmount) {
        val cardEntity =
            cardsDao.getCardByNumber(cardId) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        val updated = cardEntity.copy(recentBalance = cardEntity.recentBalance + amount.value)
        cardsDao.updateCard(updated)
    }

    override suspend fun sendFromCard(cardId: String, amount: MoneyAmount, contactId: Long) {
        val cardEntity =
            cardsDao.getCardByNumber(cardId) ?: throw AppError(ErrorType.CARD_NOT_FOUND)
        if (cardEntity.recentBalance < amount.value) throw AppError(ErrorType.INSUFFICIENT_CARD_BALANCE)
        val updated = cardEntity.copy(recentBalance = cardEntity.recentBalance - amount.value)
        cardsDao.updateCard(updated)
    }

    private suspend fun calculateBalance(): MoneyAmount {
        //Get the main account
        val mainAccount = accountsDao.getAccountFilteredByPriority()!!.first()
        val agency = agencyDao.getAgencyById(mainAccount.agencyId)!!

        val token = prefs.pull<String>(PrefKeys.USER_TOKEN.name)

        val balanceResponse = api.balance(token, agency.code, mainAccount.number, "")

        if (balanceResponse.isSuccessful && balanceResponse.body() != null) {
            val response = balanceResponse.body()!!
            if (response.code == 0) {
                return MoneyAmount(response.value.toFloat())
            }
        }

        return MoneyAmount(0f)
    }

    companion object {
        private const val MOCK_OBSERVING_DELAY = 5000L
    }
}