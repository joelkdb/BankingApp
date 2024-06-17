package fr.utbm.bindoomobile.domain.repositories

import fr.utbm.bindoomobile.domain.models.feature_app_lock.AuthenticationResult
import fr.utbm.bindoomobile.domain.models.feature_app_lock.BiometricsAvailability


interface AppLockRepository {
    fun setupAppLock(pinCode: String)
    fun authenticateWithPin(pin: String): AuthenticationResult
    fun checkIfAppLocked(): Boolean
    fun checkBiometricsAvailable(): BiometricsAvailability
    fun setupLockWithBiometrics(isLocked: Boolean)
    fun checkIfAppLockedWithBiometrics(): Boolean
}