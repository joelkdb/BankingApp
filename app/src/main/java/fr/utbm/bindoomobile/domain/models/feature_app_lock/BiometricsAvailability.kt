package fr.utbm.bindoomobile.domain.models.feature_app_lock

sealed class BiometricsAvailability {
    object Checking: BiometricsAvailability()
    object Available: BiometricsAvailability()
    object NotAvailable: BiometricsAvailability()
    object NotEnabled: BiometricsAvailability()
}