package fr.utbm.bindoomobile.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import fr.utbm.bindoomobile.data.datasource.local.entities.TransactionEntity
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionType

@Dao
interface TransactionDao {
    @Query("SELECT * FROM transactions WHERE id = (:id)")
    suspend fun getTransaction(id: Long): TransactionEntity?

    @Query("SELECT * FROM transactions ORDER BY id DESC LIMIT :loadSize OFFSET :startPosition")
    suspend fun getTransactionList(startPosition: Int, loadSize: Int): List<TransactionEntity>

    @Query("SELECT * FROM transactions WHERE type = :filterType ORDER BY id DESC LIMIT :loadSize OFFSET :startPosition")
    suspend fun getTransactionList(filterType: TransactionType, startPosition: Int, loadSize: Int): List<TransactionEntity>

    @Insert
    suspend fun addTransaction(transactionEntity: TransactionEntity): Long

    @Update
    suspend fun updateTransaction(card: TransactionEntity)
}