package fr.utbm.bindoomobile.data.datasource.local.entities

import androidx.room.Entity
import androidx.room.PrimaryKey
import fr.utbm.bindoomobile.domain.models.feature_cards.CardType

@Entity(tableName = "cards_cache")
data class CardEntity(
    @PrimaryKey(autoGenerate = false)
    val number: String,
    val isPrimary: Boolean,
    val cardType: CardType,
    val recentBalance: Float,
    val cardHolder: String,
    val expiration: Long,
    val addressFirstLine: String,
    val addressSecondLine: String,
    val addedDate: Long,
)
