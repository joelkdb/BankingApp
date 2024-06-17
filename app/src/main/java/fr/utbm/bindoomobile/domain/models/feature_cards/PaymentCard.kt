package fr.utbm.bindoomobile.domain.models.feature_cards

import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount


data class PaymentCard(
    val cardId: String,
    val isPrimary: Boolean,
    val cardNumber: String,
    val cardType: CardType,
    val cardHolder: String,
    val expiration: Long,
    val recentBalance: MoneyAmount,
    val addressFirstLine: String,
    val addressSecondLine: String,
    val addedDate: Long
)
