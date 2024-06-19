package fr.utbm.bindoomobile.data.repositories

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import androidx.paging.map
import androidx.work.Constraints
import androidx.work.Data
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.OutOfQuotaPolicy
import androidx.work.WorkManager
import com.cioccarellia.ksprefs.KsPrefs
import fr.utbm.bindoomobile.data.app.PrefKeys
import fr.utbm.bindoomobile.data.datasource.local.dao.AccountDao
import fr.utbm.bindoomobile.data.datasource.local.dao.AgencyDao
import fr.utbm.bindoomobile.data.datasource.local.dao.TransactionDao
import fr.utbm.bindoomobile.data.datasource.local.entities.TransactionEntity
import fr.utbm.bindoomobile.data.datasource.remote.api.ClientApiService
import fr.utbm.bindoomobile.data.workers.TransactionWorker
import fr.utbm.bindoomobile.domain.core.AppError
import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.models.feature_transactions.Transaction
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionRowPayload
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionStatus
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionType
import fr.utbm.bindoomobile.domain.repositories.TransactionRepository
import fr.utbm.bindoomobile.ui.core.extensions.floatToString
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

class TransactionRepositoryMock(
    private val workManager: WorkManager,
    private val transactionDao: TransactionDao,
    private val coroutineDispatcher: CoroutineDispatcher,
    private val accountDao: AccountDao,
    private val agencyDao: AgencyDao,
    private val prefs: KsPrefs,
    private val api: ClientApiService
) : TransactionRepository {
    override suspend fun getTransactions(filterByType: TransactionType?): Flow<PagingData<Transaction>> {

        return Pager(
            config = PagingConfig(
                pageSize = PAGE_MAX_SIZE,
                initialLoadSize = PAGE_MAX_SIZE,
                prefetchDistance = PREFETCH_DISTANCE
            ),
            pagingSourceFactory = {
                TransactionSource(
                    filterByType = filterByType,
                    transactionDao = transactionDao,
                    accountDao = accountDao,
                    agencyDao = agencyDao,
                    prefs = prefs,
                    api = api
                )
            }
        ).flow.map {
            it.map { cachedTx ->
                mapTransactionFromCache(cachedTx)
            }
        }
    }

    override fun getTransactionStatusFlow(transactionId: Long): Flow<TransactionStatus> {
        return flow {
            while (true) {
                val tx = transactionDao.getTransaction(transactionId)
                    ?: throw AppError(ErrorType.TRANSACTION_NOT_FOUND)
                emit(tx.recentStatus)

                delay(MOCK_TRANSACTION_STATUS_CHECK_DELAY)
            }
        }.flowOn(coroutineDispatcher)
    }

    override suspend fun submitTransaction(payload: TransactionRowPayload) {
        val raw = TransactionEntity(
            type = payload.type,
            value = payload.amount,
            createdDate = System.currentTimeMillis(),
            recentStatus = TransactionStatus.PENDING,
            updatedStatusDate = System.currentTimeMillis(),
            accountId = payload.accountId,
            reference = "",
            label = ""
        )
        val success = sendMoneyFromApi(payload)
        if (success) {
            raw.recentStatus = TransactionStatus.COMPLETED
        } else {
            raw.recentStatus = TransactionStatus.FAILED
        }

        val savedId = transactionDao.addTransaction(raw)

        val data = Data.Builder()
            .putLong(TransactionWorker.TRANSACTION_ID_KEY, savedId)
            .build()

        val workRequest =
            OneTimeWorkRequestBuilder<TransactionWorker>()
                .setConstraints(
                    Constraints.Builder()
                        .setRequiredNetworkType(NetworkType.CONNECTED)
                        .setRequiresBatteryNotLow(false)
                        .build()
                )
                .setExpedited(OutOfQuotaPolicy.RUN_AS_NON_EXPEDITED_WORK_REQUEST)
                .setInputData(data)
                .build()

        workManager.enqueue(workRequest)
    }

    private suspend fun sendMoneyFromApi(payload: TransactionRowPayload): Boolean {
        val mainAccount = accountDao.getAccountFilteredByPriority()!!.first()
        val agency = agencyDao.getAgencyById(mainAccount.agencyId)!!

        val token = prefs.pull<String>(PrefKeys.USER_TOKEN.name)

        val transferResponse = api.transfer(
            token = token,
            agency = agency.code,
            account = payload.accountId,
            daccount = payload.destinationId,
            amount = payload.amount.value.floatToString(),
            comment = payload.comment
        )
        if (transferResponse.isSuccessful && transferResponse.body() != null) {
            val response = transferResponse.body()!!
            if (response.code == 0) {
                return true
            }
        }
        return false
    }

    private fun mapTransactionFromCache(
        entity: TransactionEntity
    ): Transaction {
        return Transaction(
            id = entity.id,
            type = entity.type,
            label = entity.label,
            value = entity.value,
            recentStatus = entity.recentStatus,
            createdDate = entity.createdDate,
            updatedStatusDate = entity.updatedStatusDate
        )
    }

    companion object {
        private const val PAGE_MAX_SIZE = 30
        private const val PREFETCH_DISTANCE = 5
        private const val MOCK_TRANSACTION_STATUS_CHECK_DELAY = 5000L
    }

    data class Contact(
        val id: Long,
        val name: String,
        val profilePic: String,
        val linkedCardNumber: String
    )
}