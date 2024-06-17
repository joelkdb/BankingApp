package fr.utbm.bindoomobile.ui.feature_account.components.account_actions

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import fr.utbm.bindoomobile.R

sealed class AccountAction(
    @DrawableRes val icon: Int,
    @StringRes val uiTitle: Int,
) {
    object SendMoney : AccountAction(R.drawable.ic_send, R.string.send)
    object RequestMoney : AccountAction(R.drawable.ic_request, R.string.request)
    object TopUp : AccountAction(R.drawable.ic_topup, R.string.top_up)
    object Pay : AccountAction(R.drawable.ic_pay, R.string.pay)

}
