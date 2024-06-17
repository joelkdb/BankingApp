package fr.utbm.bindoomobile.domain.usecases.onboarding

import fr.utbm.bindoomobile.domain.repositories.AppSettignsRepository


class CheckIfPassedOnboardingUseCase(
    private val settignsRepository: AppSettignsRepository
) {
    fun execute(): Boolean {
        return settignsRepository.isOnboardingPassed()
    }
}