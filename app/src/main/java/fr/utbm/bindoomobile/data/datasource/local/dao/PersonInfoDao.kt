package fr.utbm.bindoomobile.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.utbm.bindoomobile.data.datasource.local.entities.PersonInfoEntity
import fr.utbm.bindoomobile.data.datasource.local.entities.SFDEntity

@Dao
interface PersonInfoDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addPersonInfo(personInfo: PersonInfoEntity)

    @Query("SELECT * FROM persons_infos")
    suspend fun getPersonInfos(): List<PersonInfoEntity>

    @Query("SELECT * FROM persons_infos WHERE code = (:code)")
    suspend fun getPersonInfoByCode(code: String): PersonInfoEntity?

    @Delete
    suspend fun deletePersonInfo(personInfo: PersonInfoEntity)

    @Update
    suspend fun updatePersonInfo(personInfo: PersonInfoEntity)

}
