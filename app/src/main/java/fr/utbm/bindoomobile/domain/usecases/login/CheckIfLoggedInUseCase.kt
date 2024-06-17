package fr.utbm.bindoomobile.domain.usecases.login

import fr.utbm.bindoomobile.domain.repositories.LoginRepository

class CheckIfLoggedInUseCase(
    private val loginRepository: LoginRepository
) {
    suspend fun execute(): Boolean {
        return loginRepository.checkIfLoggedIn()
    }
}