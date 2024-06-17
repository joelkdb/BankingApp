package fr.utbm.bindoomobile.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "sfds",
    foreignKeys = [
        ForeignKey(
            entity = CountryEntity::class,
            parentColumns = arrayOf("countryId"),
            childColumns = arrayOf("countryId"),
            onUpdate = ForeignKey.CASCADE,
        )]
)
data class SFDEntity(
    @PrimaryKey(autoGenerate = true)
    val sfdId: Long = 0,
    val code: String,
    val label: String,
    val logo: String,
    val countryId: Long
)
