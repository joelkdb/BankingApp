package fr.utbm.bindoomobile.ui.feature_cards.dialog_card_picker

import fr.utbm.bindoomobile.ui.feature_cards.model.CardUi
import fr.utbm.bindoomobile.ui.core.resources.UiText

data class CardPickerState(
    val isLoading: Boolean = false,
    val cards: List<CardUi>? = null,
    val selectedCardId: String? = null,
    val error: UiText? = null,
)
