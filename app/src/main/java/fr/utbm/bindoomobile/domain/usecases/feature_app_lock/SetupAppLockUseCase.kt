package fr.utbm.bindoomobile.domain.usecases.feature_app_lock

import fr.utbm.bindoomobile.domain.repositories.AppLockRepository

class SetupAppLockUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(pinCode: String) {
        return appLockRepository.setupAppLock(pinCode)
    }
}