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
import kotlinx.coroutines.withContext

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

    override suspend fun getAccountById(accountId: String): AccountEntity =
        withContext(coroutineDispatcher) {
            val accountEntity = accountsDao.getAccountByNumber(accountId) ?: throw AppError(
                ErrorType.ACCOUNT_NOT_FOUND
            )
            delay(MOCK_DELAY)
            return@withContext accountEntity
        }

    override suspend fun getAccounts(): List<AccountEntity> {
        return accountsDao.getAccounts()
    }

    override fun getBalanceFlow(): Flow<MoneyAmount> {
        return flow {
            while (true) {
                emit(calculateBalance())
                delay(MOCK_OBSERVING_DELAY)
            }
        }.flowOn(coroutineDispatcher)
    }

    override suspend fun getRecipient(recipientAccount: String): String {
        val mainAccount = getMainAccount()
        val agency = agencyDao.getAgencyById(mainAccount.agencyId)!!

        val token = prefs.pull<String>(PrefKeys.USER_TOKEN.name)

        val memberResponse = api.memberInfos(token, agency.code, recipientAccount)

        if (memberResponse.isSuccessful && memberResponse.body() != null) {
            val response = memberResponse.body()!!
            if (response.code == 0) {
                return response.member?.fullName ?: ""
            }
        }
        return ""
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

    private suspend fun calculateBalance(): MoneyAmount {
        //Get the main account
        val mainAccount = accountsDao.getAccountFilteredByPriority()!!.first()
        val agency = agencyDao.getAgencyById(mainAccount.agencyId)!!

        val token = prefs.pull<String>(PrefKeys.USER_TOKEN.name)

        val balanceResponse = api.balance(token, agency.code, mainAccount.number)

        if (balanceResponse.isSuccessful && balanceResponse.body() != null) {
            val response = balanceResponse.body()!!
            if (response.code == 0) {
                mainAccount.balance = response.value.toDouble()
                //Update account balance
                accountsDao.updateAccount(mainAccount)
                return MoneyAmount(response.value.toFloat())
            }
        }

        return MoneyAmount(0f)
    }

    companion object {
        private const val MOCK_DELAY = 500L
        private const val MOCK_OBSERVING_DELAY = 5000L
    }
}