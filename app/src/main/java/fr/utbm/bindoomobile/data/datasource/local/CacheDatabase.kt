package fr.utbm.bindoomobile.data.datasource.local

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import fr.utbm.bindoomobile.data.datasource.local.converters.MoneyAmountConvertor
import fr.utbm.bindoomobile.data.datasource.local.dao.AccountDao
import fr.utbm.bindoomobile.data.datasource.local.dao.AgencyDao
import fr.utbm.bindoomobile.data.datasource.local.dao.CardsDao
import fr.utbm.bindoomobile.data.datasource.local.dao.CountryDao
import fr.utbm.bindoomobile.data.datasource.local.dao.PersonInfoDao
import fr.utbm.bindoomobile.data.datasource.local.dao.SFDDao
import fr.utbm.bindoomobile.data.datasource.local.dao.TransactionDao
import fr.utbm.bindoomobile.data.datasource.local.entities.AccountEntity
import fr.utbm.bindoomobile.data.datasource.local.entities.AgencyEntity
import fr.utbm.bindoomobile.data.datasource.local.entities.CardEntity
import fr.utbm.bindoomobile.data.datasource.local.entities.CountryEntity
import fr.utbm.bindoomobile.data.datasource.local.entities.PersonInfoEntity
import fr.utbm.bindoomobile.data.datasource.local.entities.SFDEntity
import fr.utbm.bindoomobile.data.datasource.local.entities.TransactionEntity

@Database(
    entities = [AccountEntity::class, AgencyEntity::class, CardEntity::class, CountryEntity::class,
        PersonInfoEntity::class, SFDEntity::class, TransactionEntity::class],
    version = 1,
    exportSchema = false
)
@TypeConverters(MoneyAmountConvertor::class)
abstract class CacheDatabase : RoomDatabase() {
    abstract fun getAccountDao(): AccountDao
    abstract fun getAgencyDao(): AgencyDao
    abstract fun getCardsDao(): CardsDao
    abstract fun getCountryDao(): CountryDao
    abstract fun getPersonInfoDao(): PersonInfoDao
    abstract fun getSfdDao(): SFDDao
    abstract fun getTransactionsDao(): TransactionDao
}
