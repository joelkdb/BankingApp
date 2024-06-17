package fr.utbm.bindoomobile.ui.feature_account

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.ui.feature_cards.model.CardUi

data class CardPickerState(
    val isLoading: Boolean = false,
    val showCardPicker: Boolean = false,
    val selectedCard: CardUi? = null,
    val cardSelectErrorEvent: StateEventWithContent<ErrorType> = consumed()
)