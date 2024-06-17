package fr.utbm.bindoomobile.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "countries")
data class CountryEntity(
    @PrimaryKey(autoGenerate = true)
    val countryId: Long?,
    val code: String,
    val label: String
)
