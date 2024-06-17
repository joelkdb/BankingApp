package fr.utbm.bindoomobile.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.utbm.bindoomobile.data.datasource.local.entities.AccountEntity

@Dao
interface AccountDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addAccount(account: AccountEntity)

    @Query("SELECT * FROM accounts ORDER by number")
    suspend fun getAccounts(): List<AccountEntity>

    @Query("SELECT * FROM accounts WHERE number = (:number)")
    suspend fun getAccountByNumber(number: String): AccountEntity?

    @Query("SELECT * FROM accounts WHERE code = (:code)")
    suspend fun getAccountByCode(code: String): AccountEntity?

    @Query("SELECT * FROM accounts ORDER BY priority ASC")
    suspend fun getAccountFilteredByPriority(): List<AccountEntity>?

    @Delete
    suspend fun deleteAccount(account: AccountEntity)

    @Update
    suspend fun updateAccount(account: AccountEntity)

}