package fr.utbm.bindoomobile.domain.usecases.login

import fr.utbm.bindoomobile.domain.repositories.LoginRepository

class LogoutUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun execute() {
        return loginRepository.logOut()
    }
}