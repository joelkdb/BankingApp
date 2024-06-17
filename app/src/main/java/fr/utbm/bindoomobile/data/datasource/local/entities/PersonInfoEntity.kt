package fr.utbm.bindoomobile.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "persons_infos")
data class PersonInfoEntity(
    @PrimaryKey(autoGenerate = true)
    val personId: Long?,
    val code: String,
    val firstname: String,
    val lastname: String
)
