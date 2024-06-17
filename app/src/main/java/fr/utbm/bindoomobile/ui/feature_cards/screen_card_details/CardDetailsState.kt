package fr.utbm.bindoomobile.ui.feature_cards.screen_card_details

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.domain.core.OperationResult
import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_cards.model.CardUi

data class CardDetailsState(
    val card: CardUi? = null,
    val showCardSkeleton: Boolean = true,
    val error: UiText? = null,
    val showLoading: Boolean = false,
    val showDeleteCardDialog: Boolean = false,
    val cardDeletedResultEvent: StateEventWithContent<OperationResult<Unit>> = consumed(),
    val setCardAsPrimaryEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
)
