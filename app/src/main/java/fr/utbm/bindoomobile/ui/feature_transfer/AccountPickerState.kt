package fr.utbm.bindoomobile.ui.feature_transfer

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.ui.feature_transfer.model.AccountUi

data class AccountPickerState(
    val isLoading: Boolean = false,
    val showAccountPicker: Boolean = false,
    val selectedAccount: AccountUi? = null,
    val accountSelectErrorEvent: StateEventWithContent<ErrorType> = consumed()
)