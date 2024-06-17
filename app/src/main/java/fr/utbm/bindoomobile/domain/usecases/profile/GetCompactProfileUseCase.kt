package fr.utbm.bindoomobile.domain.usecases.profile

import fr.utbm.bindoomobile.domain.models.feature_profile.CompactProfile
import fr.utbm.bindoomobile.domain.repositories.ProfileRepository

class GetCompactProfileUseCase(
    private val profileRepository: ProfileRepository
) {
    suspend fun execute(): CompactProfile {
        return profileRepository.getCompactProfile()
    }
}