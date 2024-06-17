package fr.utbm.bindoomobile.data.datasource.local.converters

import androidx.room.ProvidedTypeConverter
import androidx.room.TypeConverter
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount

@ProvidedTypeConverter
class MoneyAmountConvertor {
    @TypeConverter
    fun moneyAmountToDb(value: MoneyAmount?): Float? {
        return value?.value
    }

    @TypeConverter
    // FIXME currency when needed
    fun moneyAmountFromDb(value: Float?): MoneyAmount? {
        return if (value != null) {
            MoneyAmount(value)
        }
        else {
            null
        }
    }
}