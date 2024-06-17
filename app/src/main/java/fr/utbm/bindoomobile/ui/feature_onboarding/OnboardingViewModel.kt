package fr.utbm.bindoomobile.ui.feature_onboarding

import androidx.lifecycle.ViewModel
import fr.utbm.bindoomobile.domain.usecases.onboarding.PassOnboardingUseCase

class OnboardingViewModel(
    private val passOnboardingUseCase: PassOnboardingUseCase
) : ViewModel() {

    fun emitIntent(onboardingIntent: OnboardingIntent) {
        when (onboardingIntent) {
            is OnboardingIntent.CompleteOnboarding -> {
                passOnboardingUseCase.execute()
            }
        }
    }
}