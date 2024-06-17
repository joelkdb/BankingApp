package fr.utbm.bindoomobile.ui.feature_app_lock.core.biometrics

sealed class BiometricAuthResult {
    object Success: BiometricAuthResult()
    data class Failure(val error: String): BiometricAuthResult()
}