package fr.utbm.bindoomobile.ui.feature_home.model

import fr.utbm.bindoomobile.ui.core.resources.UiText
import fr.utbm.bindoomobile.ui.feature_account.MoneyAmountUi
import fr.utbm.bindoomobile.ui.feature_cards.model.CardUi
import fr.utbm.bindoomobile.ui.feature_profile.model.ProfileUi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

sealed class HomeState {
    // Single loading for all parts of screen for simplicity
    object Loading : HomeState()

    data class Success(
        val profile: ProfileUi,
        val cards: List<CardUi> = emptyList(),
        //val savings: List<SavingUi> = emptyList(),
        val accountNumber: String = "",
        val balance: Flow<MoneyAmountUi?> = flowOf(null),
    ) : HomeState()

    data class Error(val error: UiText) : HomeState()
}
