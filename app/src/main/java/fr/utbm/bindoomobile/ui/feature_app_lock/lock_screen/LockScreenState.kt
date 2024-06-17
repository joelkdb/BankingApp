package fr.utbm.bindoomobile.ui.feature_app_lock.lock_screen

import de.palm.composestateevents.StateEvent
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_app_lock.components.AppLockUiState
import fr.utbm.bindoomobile.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import fr.utbm.bindoomobile.ui.feature_app_lock.core.biometrics.BiometricsPromptUi
import fr.utbm.bindoomobile.ui.feature_logout.LogoutState

data class LockScreenState(
    val uiState: AppLockUiState = AppLockUiState(),
    val biometricsPromptState: BiometricsPromptUi = BiometricsPromptUi(
        title = UiText.StringResource(R.string.unlock_app_biometrics),
        cancelBtnText = UiText.StringResource(R.string.cancel)
    ),

    val showBiometricsPromptEvent: StateEvent = consumed,
    val unlockWithPinEvent: StateEvent = consumed,
    val unlockWithBiometricsResultEvent: StateEventWithContent<BiometricAuthResult> = consumed(),

    val logoutState: LogoutState = LogoutState(),
)
