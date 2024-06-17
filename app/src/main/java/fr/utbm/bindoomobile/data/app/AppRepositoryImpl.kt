package fr.utbm.bindoomobile.data.app

import com.cioccarellia.ksprefs.KsPrefs
import fr.utbm.bindoomobile.domain.repositories.AppSettignsRepository

class AppRepositoryImpl(
    private val prefs: KsPrefs
) : AppSettignsRepository {
    override fun setOnboardingPassed(viewed: Boolean) {
        prefs.push(PrefKeys.IS_WIZARD_VIEWED.name, viewed)
    }

    override fun isOnboardingPassed(): Boolean {
        return prefs.pull(PrefKeys.IS_WIZARD_VIEWED.name, false)
    }

    override fun isAppPermissionAlreadyAsked(permission: String): Boolean {
        val key = "${PrefKeys.PERMISSION_ASKED_FLAG_PREFIX}_${permission}"
        return prefs.pull(key, false)
    }

    override fun setPermissionAsked(permission: String, isAsked: Boolean) {
        val key = "${PrefKeys.PERMISSION_ASKED_FLAG_PREFIX}_${permission}"
        prefs.push(key, true)
    }
}