package fr.utbm.bindoomobile.domain.usecases.onboarding

import fr.utbm.bindoomobile.domain.repositories.AppSettignsRepository

class PassOnboardingUseCase(
    private val settignsRepository: AppSettignsRepository
) {
    fun execute() {
        settignsRepository.setOnboardingPassed(viewed = true)
    }
}