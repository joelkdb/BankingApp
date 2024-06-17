package fr.utbm.bindoomobile.ui.feature_app_lock.setup_applock.biometrics

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.domain.models.feature_app_lock.BiometricsAvailability
import fr.utbm.bindoomobile.ui.feature_app_lock.core.biometrics.BiometricAuthResult
import fr.utbm.bindoomobile.ui.feature_app_lock.core.biometrics.BiometricsPromptUi

data class EnableBiometricsState(
    val prompt: BiometricsPromptUi,
    val biometricsAvailability: BiometricsAvailability,
    val authResultEvent: StateEventWithContent<BiometricAuthResult> = consumed()
)
