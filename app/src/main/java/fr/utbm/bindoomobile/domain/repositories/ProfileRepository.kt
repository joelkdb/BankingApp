package fr.utbm.bindoomobile.domain.repositories

import fr.utbm.bindoomobile.domain.models.feature_profile.CompactProfile


interface ProfileRepository {
    suspend fun getCompactProfile(): CompactProfile
}