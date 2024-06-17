package fr.utbm.bindoomobile.ui.feature_app_lock.core

import fr.utbm.bindoomobile.ui.feature_app_lock.core.biometrics.BiometricsIntent

interface BiometricsViewModel {
    fun emitBiometricsIntent(intent: BiometricsIntent)
}