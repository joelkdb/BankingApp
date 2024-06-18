package fr.utbm.bindoomobile.data.repositories

import androidx.paging.PagingSource
import androidx.paging.PagingState
import com.cioccarellia.ksprefs.KsPrefs
import fr.utbm.bindoomobile.data.app.PrefKeys
import fr.utbm.bindoomobile.data.datasource.local.dao.AccountDao
import fr.utbm.bindoomobile.data.datasource.local.dao.AgencyDao
import fr.utbm.bindoomobile.data.datasource.local.dao.TransactionDao
import fr.utbm.bindoomobile.data.datasource.local.entities.TransactionEntity
import fr.utbm.bindoomobile.data.datasource.remote.api.ClientApiService
import fr.utbm.bindoomobile.data.datasource.remote.dtos.Statement
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionStatus
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionType
import fr.utbm.bindoomobile.ui.core.extensions.convertDateToMillis

class TransactionSource(
    private val transactionDao: TransactionDao,
    private val filterByType: TransactionType?,
    private val accountDao: AccountDao,
    private val agencyDao: AgencyDao,
    private val prefs: KsPrefs,
    private val api: ClientApiService
) : PagingSource<Int, TransactionEntity>() {
    override fun getRefreshKey(state: PagingState<Int, TransactionEntity>): Int? {
        return state.anchorPosition
    }

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, TransactionEntity> {
        return try {
            val currentPage = params.key ?: 1

            val transactions = loadFromDbCache(
                filterByType = filterByType,
                params = params
            )

            LoadResult.Page(
                data = transactions,
                prevKey = if (currentPage == 1) null else currentPage - 1,
                nextKey = if (transactions.size < params.loadSize) null else currentPage + 1
            )

        } catch (e: Exception) {
            return LoadResult.Error(e)
        }
    }

    private suspend fun loadFromDbCache(
        filterByType: TransactionType?,
        params: LoadParams<Int>,
    ): List<TransactionEntity> {
        val currentPage = params.key ?: 1
        val startPosition = (currentPage - 1) * params.loadSize

        val statements = getFromRemote()

        return when (filterByType) {
            null -> return statements

            TransactionType.RECEIVE -> return statements.filter { it.type == TransactionType.RECEIVE }

            TransactionType.SEND -> return statements.filter { it.type == TransactionType.SEND }

            else -> transactionDao.getTransactionList(
                filterType = filterByType,
                startPosition = startPosition,
                loadSize = params.loadSize
            )
        }
    }

    private suspend fun getFromRemote(): List<TransactionEntity> {
        //Get the main account
        val mainAccount = accountDao.getAccountFilteredByPriority()!!.first()
        val agency = agencyDao.getAgencyById(mainAccount.agencyId)!!

        val token = prefs.pull<String>(PrefKeys.USER_TOKEN.name)

        val statementResponse = api.statement(token, agency.code, mainAccount.number, 0, "")
        if (statementResponse.isSuccessful && statementResponse.body() != null) {
            val response = statementResponse.body()!!
            if (response.code == 0) {
                return this.mapStatementsToTransactionEntities(response.statements!!)
            }
        }
        return emptyList()
    }

    private fun mapStatementsToTransactionEntities(statements: List<Statement>): List<TransactionEntity> {
        return statements.map { statement ->
            TransactionEntity(
                type = if (statement.amount < 0) TransactionType.SEND else TransactionType.RECEIVE,
                value = MoneyAmount(statement.amount.toFloat()),
                recentStatus = TransactionStatus.COMPLETED,
                cardId = "1", // TODO
                createdDate = statement.valueDateText.convertDateToMillis(),
                updatedStatusDate = System.currentTimeMillis(),
                reference = statement.reference,
                label = statement.label
            ).apply {
                //TODO
            }
        }
    }
}