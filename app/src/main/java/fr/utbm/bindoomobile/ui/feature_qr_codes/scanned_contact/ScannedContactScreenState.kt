package fr.utbm.bindoomobile.ui.feature_qr_codes.scanned_contact

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.domain.core.OperationResult
import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_contact.model.ContactUi

data class ScannedContactScreenState(
    val isContactLoading: Boolean = true,
    val isLoading: Boolean = false,
    val contact: ContactUi? = null,
    val error: UiText? = null,
    val addContactResEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
)
