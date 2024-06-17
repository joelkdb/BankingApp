package fr.utbm.bindoomobile.ui.app_host.navigation.model

data class ConditionalNavigation(
    val requireLogin: Boolean,
    val requireOnboarding: Boolean,
    val requireCreateAppLock: Boolean,
)
