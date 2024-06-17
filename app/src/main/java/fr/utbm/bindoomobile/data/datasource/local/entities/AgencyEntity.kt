package fr.utbm.bindoomobile.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "agencies",
    foreignKeys = [
        ForeignKey(
            entity = SFDEntity::class,
            parentColumns = arrayOf("sfdId"),
            childColumns = arrayOf("sfdId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )]
)
data class AgencyEntity(
    @PrimaryKey(autoGenerate = true)
    val agencyId: Long = 0,
    val code: String,
    val label: String,
    val sfdId: Long
)
