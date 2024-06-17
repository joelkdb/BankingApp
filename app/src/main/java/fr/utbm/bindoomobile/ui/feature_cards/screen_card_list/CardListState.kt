package fr.utbm.bindoomobile.ui.feature_cards.screen_card_list

import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_cards.model.CardUi

data class CardListState(
    val isLoading: Boolean = true,
    val cards: List<CardUi> = emptyList(),
    val floatingAddCardShown: Boolean = false,
    val error: UiText? = null,
)
