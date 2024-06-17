package fr.utbm.bindoomobile.core.di.domain

import fr.utbm.bindoomobile.domain.usecases.onboarding.CheckIfPassedOnboardingUseCase
import fr.utbm.bindoomobile.domain.usecases.onboarding.PassOnboardingUseCase
import org.koin.dsl.module

val onboardingModule = module {
    factory {
        CheckIfPassedOnboardingUseCase(
            settignsRepository = get()
        )
    }
    factory {
        PassOnboardingUseCase(
            settignsRepository = get()
        )
    }
}