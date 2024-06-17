package fr.utbm.bindoomobile.domain.models.feature_app_lock

sealed class AuthenticationResult {
    object Success: AuthenticationResult()

    data class Failure(
        val remainingAttempts: Int?
    ): AuthenticationResult()
}
