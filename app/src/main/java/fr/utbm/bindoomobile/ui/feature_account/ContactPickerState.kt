package fr.utbm.bindoomobile.ui.feature_account


import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.domain.core.ErrorType

data class ContactPickerState(
    val isLoading: Boolean = false,
    val showContactPicker: Boolean = false,
    //val selectedContact: ContactUi? = null,
    val contactSelectedErrorEvent: StateEventWithContent<ErrorType> = consumed()
)
