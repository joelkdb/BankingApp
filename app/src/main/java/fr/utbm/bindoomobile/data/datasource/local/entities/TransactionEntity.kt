package fr.utbm.bindoomobile.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionStatus
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionType

@Entity(tableName = "transactions")
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true)
    val id: Long = 0,
    val type: TransactionType,
    val value: MoneyAmount,
    val recentStatus: TransactionStatus,
    val cardId: String,
    val linkedContactId: Long? = null,
    val createdDate: Long,
    val updatedStatusDate: Long,
)
