package fr.utbm.bindoomobile.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.utbm.bindoomobile.data.datasource.local.entities.SFDEntity

@Dao
interface SFDDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addSFD(sfd: SFDEntity)

    @Query("SELECT * FROM sfds")
    suspend fun getSFDs(): List<SFDEntity>

    @Query("SELECT * FROM sfds WHERE sfdId = (:id)")
    suspend fun getSFDById(id: Long): SFDEntity?

    @Query("SELECT * FROM sfds WHERE code = (:code)")
    suspend fun getSFDByCode(code: String): SFDEntity?

    @Delete
    suspend fun deleteSFD(sfd: SFDEntity)

    @Update
    suspend fun updateSFD(sfd: SFDEntity)

}
