package fr.utbm.bindoomobile.domain.usecases.feature_app_lock

import fr.utbm.bindoomobile.domain.models.feature_app_lock.AuthenticationResult
import fr.utbm.bindoomobile.domain.repositories.AppLockRepository

class AuthenticateWithPinUseCase(
    private val appLockRepository: AppLockRepository
) {
    fun execute(pin: String): AuthenticationResult {
        return appLockRepository.authenticateWithPin(pin)
    }
}