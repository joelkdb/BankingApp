package fr.utbm.bindoomobile.domain.usecases.feature_app_lock

import fr.utbm.bindoomobile.domain.models.feature_app_lock.BiometricsAvailability
import fr.utbm.bindoomobile.domain.repositories.AppLockRepository


class CheckIfBiometricsAvailableUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(): BiometricsAvailability {
        return appLockRepository.checkBiometricsAvailable()
    }
}