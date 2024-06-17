package fr.utbm.bindoomobile.domain.models.feature_transactions

import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount

data class Transaction(
    val id: Long,
    val type: TransactionType,
    val value: MoneyAmount,
    val recentStatus: TransactionStatus,
    val createdDate: Long,
    val updatedStatusDate: Long,
)
