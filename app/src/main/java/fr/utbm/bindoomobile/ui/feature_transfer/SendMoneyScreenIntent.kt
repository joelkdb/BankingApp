package fr.utbm.bindoomobile.ui.feature_transfer

import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount

sealed class SendMoneyScreenIntent {
    data class EnterScreen(val selectedAccountId: String?) : SendMoneyScreenIntent()
    data class ChooseAccount(val accountId: String) : SendMoneyScreenIntent()
    //data class ChooseContact(val contactId: Long) : SendMoneyScreenIntent()

    //    object RefreshCard : SendMoneyScreenIntent()
    data class UpdateSelectedValue(val amount: MoneyAmount) : SendMoneyScreenIntent()
    data class ToggleAccountPicker(val show: Boolean) : SendMoneyScreenIntent()
    object ProceedClick : SendMoneyScreenIntent()
    object DismissSuccessDialog : SendMoneyScreenIntent()

    data class StringFieldChanged(
        val fieldType: SendMoneyFieldType,
        val fieldValue: String
    ) : SendMoneyScreenIntent()

    object SearchRecipient : SendMoneyScreenIntent()
}

enum class SendMoneyFieldType {
    RECIPIENT_ACCOUNT,
    COMMENT,
}

