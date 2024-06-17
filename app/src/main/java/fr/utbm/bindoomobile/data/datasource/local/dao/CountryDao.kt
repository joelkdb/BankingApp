package fr.utbm.bindoomobile.data.datasource.local.dao

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import fr.utbm.bindoomobile.data.datasource.local.entities.CountryEntity

@Dao
interface CountryDao {
    @Insert(onConflict = OnConflictStrategy.ABORT)
    suspend fun addCountry(country: CountryEntity)

    @Query("SELECT * FROM countries")
    suspend fun getCountries(): List<CountryEntity>

    @Query("SELECT * FROM countries WHERE code = (:code)")
    suspend fun getCountryByCode(code: String): CountryEntity?

    @Delete
    suspend fun deleteCountry(country: CountryEntity)

    @Update
    suspend fun updateCountry(country: CountryEntity)

}
