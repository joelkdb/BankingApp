package fr.utbm.bindoomobile.ui.feature_cards.screen_card_details

sealed class CardDetailsIntent {
    data class EnterScreen(val cardId: String) : CardDetailsIntent()
    data class ToggleDeleteCardDialog(val isDialogShown: Boolean) : CardDetailsIntent()
    object ConfirmDeleteCard : CardDetailsIntent()
    data class SetCardAsPrimary(
        val cardId: String,
        val makePrimary: Boolean
    ) : CardDetailsIntent()
}
