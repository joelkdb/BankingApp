package fr.utbm.bindoomobile.domain.models.feature_profile

data class CompactProfile(
    val id: String,
    val firstName: String,
    val lastName: String,
    val nickName: String,
    val email: String,
    val profilePicUrl: String,
)