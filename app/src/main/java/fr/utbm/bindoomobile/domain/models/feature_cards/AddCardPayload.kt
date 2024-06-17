package fr.utbm.bindoomobile.domain.models.feature_cards

data class AddCardPayload(
    val cardNumber: String,
    val cardHolder: String,
    val addressFirstLine: String,
    val addressSecondLine: String,
    val cvvCode: String,
    val expirationDate: Long,
)
