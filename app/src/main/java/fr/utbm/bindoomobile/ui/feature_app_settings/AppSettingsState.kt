package fr.utbm.bindoomobile.ui.feature_app_settings

import fr.utbm.bindoomobile.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import fr.utbm.bindoomobile.ui.feature_app_lock.core.biometrics.BiometricsPromptUi
import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.domain.models.feature_app_lock.BiometricsAvailability
import fr.utbm.bindoomobile.ui.core.resources.UiText

data class AppSettingsState(
    val biometricPrompt: BiometricsPromptUi = BiometricsPromptUi(
        title = UiText.StringResource(R.string.unlock_app_biometrics),
        cancelBtnText = UiText.StringResource(R.string.cancel)
    ),
    val biometricsAvailability: BiometricsAvailability,
    val isAppLockedWithBiometrics: Boolean,
    val biometricsAuthEvent: StateEventWithContent<BiometricAuthResult> = consumed()
)
