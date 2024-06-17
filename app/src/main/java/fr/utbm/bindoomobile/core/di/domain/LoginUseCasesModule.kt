package fr.utbm.bindoomobile.core.di.domain

import fr.utbm.bindoomobile.domain.usecases.login.CheckIfLoggedInUseCase
import fr.utbm.bindoomobile.domain.usecases.login.LoginUseCase
import fr.utbm.bindoomobile.domain.usecases.login.LogoutUseCase
import org.koin.dsl.module

val loginUseCasesModule = module {
    factory { LoginUseCase(loginRepository = get()) }
    factory { LogoutUseCase(loginRepository = get()) }
    factory { CheckIfLoggedInUseCase(loginRepository = get()) }
}