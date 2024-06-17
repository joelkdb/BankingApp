package fr.utbm.bindoomobile.ui.feature_app_lock.setup_applock.pin

import fr.utbm.bindoomobile.ui.feature_app_lock.components.AppLockUiState
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.ui.feature_app_lock.setup_applock.pin.PinCreatedResult

data class CreatePinState(
    val initialPin: String = "",
    val confirmPin: String = "",
    val isConfirmationStage: Boolean = false,
    val uiState: AppLockUiState = AppLockUiState(),
    val pinCreatedEvent: StateEventWithContent<PinCreatedResult> = consumed()
)
