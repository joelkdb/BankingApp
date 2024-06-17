package fr.utbm.bindoomobile.domain.usecases.feature_app_lock

import fr.utbm.bindoomobile.domain.repositories.AppLockRepository

class SetupAppLockedWithBiometricsUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(isLocked: Boolean = true) {
        return appLockRepository.setupLockWithBiometrics(isLocked)
    }
}