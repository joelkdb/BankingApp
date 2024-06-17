package fr.utbm.bindoomobile.ui.feature_account

import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.ui.core.resources.UiText


data class AmountPickersState(
    val selectedAmount: MoneyAmount = MoneyAmount(0F),
    val maxAmount: MoneyAmount? = null,
    val proposedValues: Set<MoneyAmount> = emptySet(),
    val pickersEnabled: Boolean = true,
    val error: UiText? = null
)
