package fr.utbm.bindoomobile.ui.components.permissions

import androidx.annotation.DrawableRes
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.ui.core.resources.UiText

object PermissionContentHelper {
    fun getPermissionExplanationContent(permission: String): PermissionExplanation {
        return when (permission) {
            android.Manifest.permission.CAMERA -> {
                PermissionExplanation(
                    icon = R.drawable.ic_camera,
                    permissionName = UiText.StringResource(R.string.camera),
                    explanation = UiText.StringResource(R.string.camera_permission_explanation)
                )
            }

            android.Manifest.permission.POST_NOTIFICATIONS -> {
                PermissionExplanation(
                    icon = R.drawable.ic_bell_filled,
                    permissionName = UiText.StringResource(R.string.notifications),
                    explanation = UiText.StringResource(R.string.notifications_permission_explanation)
                )
            }

            else -> error("Explanation content not implemented for permission $permission")
        }
    }
}

data class PermissionExplanation(
    @DrawableRes val icon: Int,
    val permissionName: UiText,
    val explanation: UiText,
)