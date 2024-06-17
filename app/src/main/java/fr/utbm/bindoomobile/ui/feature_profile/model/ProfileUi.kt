package fr.utbm.bindoomobile.ui.feature_profile.model

import fr.utbm.bindoomobile.domain.models.feature_profile.CompactProfile


data class ProfileUi(
    val fullName: String,
    val nickName: String,
    val id: String,
    val email: String,
    val profilePicUrl: String
) {
    companion object {
        fun mock() = ProfileUi(
            fullName = "Joel Kadaba",
            nickName = "joelkdb",
            id = "sokkadaba",
            email = "soklibou.kadaba@utbm.fr",
            profilePicUrl = "https://api.dicebear.com/7.x/open-peeps/svg?seed=Bailey"
        )

        fun mapFromDomain(profile: CompactProfile) = ProfileUi(
            fullName = "${profile.firstName} ${profile.lastName}",
            nickName = profile.nickName,
            id = profile.id,
            email = profile.email,
            profilePicUrl = profile.profilePicUrl
        )
    }
}
