package fr.utbm.bindoomobile.domain.usecases.login

import fr.utbm.bindoomobile.domain.repositories.LoginRepository

class LoginUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(
        email: String,
        password: String
    ) {
        return loginRepository.login(email, password)
    }
}