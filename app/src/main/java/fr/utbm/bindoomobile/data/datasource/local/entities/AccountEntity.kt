package fr.utbm.bindoomobile.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = "accounts",
    foreignKeys = [
        ForeignKey(
            entity = AgencyEntity::class,
            parentColumns = arrayOf("agencyId"),
            childColumns = arrayOf("agencyId"),
            onUpdate = ForeignKey.CASCADE,
            onDelete = ForeignKey.CASCADE
        )]
)
data class AccountEntity(
    @PrimaryKey(autoGenerate = true)
    val accountId: Long?,
    val code: String,
    val number: String,
    val currencyCode: String,
    val accountTypeCode: String,
    val memberTypeCode: String,
    var balance: Double = 0.0,
    val accountOwner: String,
    val priority: Int,
    val enabled: Boolean,
    val agencyId: Long,
)
