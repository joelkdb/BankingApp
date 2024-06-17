package fr.utbm.bindoomobile.ui.feature_app_lock.core

import fr.utbm.bindoomobile.ui.feature_app_lock.core.AppLockIntent

interface AppLockViewModel {
    val pinLength: Int

    fun emitAppLockIntent(intent: AppLockIntent)
}