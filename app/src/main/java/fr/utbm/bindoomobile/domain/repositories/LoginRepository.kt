package fr.utbm.bindoomobile.domain.repositories

interface LoginRepository {
    suspend fun login(username: String, password: String)
    suspend fun checkIfLoggedIn(): Boolean
    suspend fun logOut()
}