package fr.utbm.bindoomobile.ui.feature_transfer

import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_account.AmountPickersState
import fr.utbm.bindoomobile.ui.feature_cards.screen_add_card.UiField
import fr.utbm.bindoomobile.ui.feature_transfer.AccountPickerState

data class SendMoneyScreenState(
    val accountPickerState: AccountPickerState = AccountPickerState(),
    val recipientAccount: UiField = UiField(""),
    val recipientName: UiField = UiField(""),
    val comment: UiField = UiField(""),
    val amountState: AmountPickersState = AmountPickersState(
        pickersEnabled = false,
    ),
    val isLoading: Boolean = false,
    val error: UiText? = null,
    val sendSubmittedEvent: StateEvent = consumed,
    val showSuccessDialog: Boolean = false,
    val requiredBackNavEvent: StateEvent = consumed
) {
    val proceedButtonEnabled
        get() = amountState.selectedAmount != MoneyAmount(0f)
                && accountPickerState.selectedAccount != null
                && !accountPickerState.isLoading
}
