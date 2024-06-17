package fr.utbm.bindoomobile.ui.feature_profile

import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_logout.LogoutState
import fr.utbm.bindoomobile.ui.feature_profile.model.ProfileUi

data class ProfileScreenState(
    val profile: ProfileUi? = null,
    val isProfileLoading: Boolean = true,
    val error: UiText? = null,
    val logoutState: LogoutState = LogoutState(),
    val showMyQrDialog: Boolean = false,
    val showScanQrDialog: Boolean = false,
    val showPermissionDialog: Boolean = false
)

