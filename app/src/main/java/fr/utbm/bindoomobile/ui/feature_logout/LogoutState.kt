package fr.utbm.bindoomobile.ui.feature_logout

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.domain.core.OperationResult

data class LogoutState(
    val isLoading: Boolean = false,
    val showLogoutDialog: Boolean = false,
    val logoutEvent: StateEventWithContent<OperationResult<Unit>> = consumed(),
)