package fr.utbm.bindoomobile.domain.usecases.feature_app_lock

import fr.utbm.bindoomobile.domain.repositories.AppLockRepository

class CheckAppLockedWithBiometricsUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(): Boolean {
        return appLockRepository.checkIfAppLockedWithBiometrics()
    }
}