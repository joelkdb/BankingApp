package fr.utbm.bindoomobile.data.workers

import android.content.Context
import android.util.Log
import androidx.work.CoroutineWorker
import androidx.work.ForegroundInfo
import androidx.work.WorkerParameters
import fr.utbm.bindoomobile.data.datasource.local.dao.TransactionDao
import fr.utbm.bindoomobile.data.datasource.local.entities.TransactionEntity
import fr.utbm.bindoomobile.data.datasource.remote.api.ClientApiService
import fr.utbm.bindoomobile.domain.core.AppError
import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.core.OperationResult
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionStatus
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionType
import fr.utbm.bindoomobile.ui.core.notifications.TransactionNotificationHelper
import kotlinx.coroutines.delay

class TransactionWorker(
    appContext: Context,
    workerParams: WorkerParameters,
    private val transactionDao: TransactionDao,
    //private val accountRepository: AccountRepository,
    private val transactionNotificationHelper: TransactionNotificationHelper
) : CoroutineWorker(appContext, workerParams) {

    companion object {
        const val TRANSACTION_ID_KEY = "transaction_id"
        private const val MOCK_TRANSACTION_DELAY = 10000L
    }

    override suspend fun doWork(): Result {
        try {
            val transactionId = inputData.getLong(TRANSACTION_ID_KEY, -1)
            Log.d("WORKER_TAG", "Run transaction operation for transaction: $transactionId")

            if (transactionId != -1L) {
                delay(MOCK_TRANSACTION_DELAY)

                val transaction = transactionDao.getTransaction(transactionId) ?: throw AppError(
                    ErrorType.TRANSACTION_NOT_FOUND
                )
                val transactionResult = executeTransaction(transaction = transaction)

                return when (transactionResult) {
                    is OperationResult.Success<*> -> {
                        transactionNotificationHelper.apply {
                            val notificationUi = successMessage(
                                transactionType = transaction.type,
                                accountId = transaction.cardId,
                                amount = transaction.value
                            )

                            showNotification(notificationUi)
                        }

                        Result.success()
                    }

                    is OperationResult.Failure -> {
                        transactionNotificationHelper.apply {
                            val notificationUi = errorMessage(transactionResult.error)
                            showNotification(notificationUi)
                        }

                        Result.failure()
                    }

                    else -> {
                        Result.failure();
                    }
                }
            } else {
                return Result.failure()
            }
        } catch (e: Exception) {
            return Result.failure()
        }
    }

    override suspend fun getForegroundInfo(): ForegroundInfo {
        val notification = transactionNotificationHelper.getNotification(
            notificationUi = transactionNotificationHelper.pending()
        )

        return ForegroundInfo(
            System.currentTimeMillis().toInt(),
            notification
        )
    }

    private suspend fun executeTransaction(transaction: TransactionEntity): OperationResult<Unit> {
        val transactionResult = when (transaction.type) {
            TransactionType.SEND -> {
                OperationResult.runWrapped {

                }
            }

            TransactionType.RECEIVE -> TODO()

            TransactionType.TOP_UP -> {
                OperationResult.runWrapped {

                }
            }
        }

        when (transactionResult) {
            is OperationResult.Failure -> {
                transactionDao.updateTransaction(
                    transaction.copy(recentStatus = TransactionStatus.FAILED)
                )
            }

            is OperationResult.Success -> {
                transactionDao.updateTransaction(
                    transaction.copy(recentStatus = TransactionStatus.COMPLETED)
                )
            }
        }

        return transactionResult
    }
}