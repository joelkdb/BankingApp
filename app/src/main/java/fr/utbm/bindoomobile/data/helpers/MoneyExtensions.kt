package fr.utbm.bindoomobile.data.helpers

import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount


inline fun <T> Iterable<T>.sumMoneyAmounts(selector: (T) -> MoneyAmount): MoneyAmount {
    var sum = MoneyAmount(0f)
    for (element in this) {
        sum += selector(element)
    }
    return sum
}