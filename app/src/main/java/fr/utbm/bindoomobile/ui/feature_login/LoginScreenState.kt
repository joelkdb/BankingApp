package fr.utbm.bindoomobile.ui.feature_login

import de.palm.composestateevents.StateEventWithContent
import de.palm.composestateevents.consumed
import fr.utbm.bindoomobile.domain.core.OperationResult
import fr.utbm.bindoomobile.ui.feature_cards.screen_add_card.UiField

data class LoginScreenState(
    val formFields: LoginFormFields = LoginFormFields(),
    val isLoading: Boolean = false,
    val loginEvent: StateEventWithContent<OperationResult<Unit>> = consumed()
) {
    companion object {
        fun mock() = LoginScreenState(
            formFields = LoginFormFields(
                loginField = UiField("samndihokubwayo"),
                passwordField = UiField("#Skadaba@98")
            )
        )
    }
}

data class LoginFormFields(
    val loginField: UiField = UiField("", null),
    val passwordField: UiField = UiField("", null),
)

enum class LoginFieldType {
    USERNAME,
    PASSWORD
}
