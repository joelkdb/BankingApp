package fr.utbm.bindoomobile.core.di.domain

import fr.utbm.bindoomobile.domain.usecases.profile.GetCompactProfileUseCase
import org.koin.dsl.module

val profileUseCasesModule = module {
    factory {  GetCompactProfileUseCase(profileRepository = get()) }
}