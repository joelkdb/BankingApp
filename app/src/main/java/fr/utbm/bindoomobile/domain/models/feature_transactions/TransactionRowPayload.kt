package fr.utbm.bindoomobile.domain.models.feature_transactions

import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount

data class TransactionRowPayload(
    val type: TransactionType,
    val amount: MoneyAmount,
    val cardId: String,
    val contactId: Long? = null,
)
