package fr.utbm.bindoomobile.core.di.data

import androidx.room.Room
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import com.cioccarellia.ksprefs.KsPrefs
import fr.utbm.bindoomobile.data.app.AppRepositoryImpl
import fr.utbm.bindoomobile.data.app_lock.AppLockRepositoryImpl
import fr.utbm.bindoomobile.data.datasource.local.CacheDatabase
import fr.utbm.bindoomobile.data.datasource.local.converters.MoneyAmountConvertor
import fr.utbm.bindoomobile.data.repositories.AccountRepositoryMock
import fr.utbm.bindoomobile.data.repositories.CardsRepositoryMock
import fr.utbm.bindoomobile.data.repositories.LoginRepositoryMock
import fr.utbm.bindoomobile.data.repositories.ProfileRepositoryMock
import fr.utbm.bindoomobile.data.repositories.TransactionRepositoryMock
import fr.utbm.bindoomobile.domain.repositories.AccountRepository
import fr.utbm.bindoomobile.domain.repositories.AppLockRepository
import fr.utbm.bindoomobile.domain.repositories.AppSettignsRepository
import fr.utbm.bindoomobile.domain.repositories.CardsRepository
import fr.utbm.bindoomobile.domain.repositories.LoginRepository
import fr.utbm.bindoomobile.domain.repositories.ProfileRepository
import fr.utbm.bindoomobile.domain.repositories.TransactionRepository
import kotlinx.coroutines.Dispatchers
import org.koin.android.ext.koin.androidApplication
import org.koin.core.qualifier.named
import org.koin.dsl.module


val dataModule = module {
    single {
        Room.databaseBuilder(
            androidApplication().applicationContext,
            CacheDatabase::class.java,
            "cache.db"
        )
            .fallbackToDestructiveMigration()
            .addTypeConverter(MoneyAmountConvertor())
            .build()
    }

    single {
        val db: CacheDatabase = get()
        db.getAccountDao()
    }

    single {
        val db: CacheDatabase = get()
        db.getAgencyDao()
    }

    single {
        val db: CacheDatabase = get()
        db.getCardsDao()
    }

    single {
        val db: CacheDatabase = get()
        db.getCountryDao()
    }

    single {
        val db: CacheDatabase = get()
        db.getPersonInfoDao()
    }

    single {
        val db: CacheDatabase = get()
        db.getSfdDao()
    }

    single {
        val db: CacheDatabase = get()
        db.getTransactionsDao()
    }

    single<AppSettignsRepository> {
        AppRepositoryImpl(get())
    }


    single<LoginRepository> {
        LoginRepositoryMock(
            coroutineDispatcher = Dispatchers.IO,
            prefs = get(),
            securedPrefs = get(named("encryptedPrefs")),
            accountDao = get(),
            countryDao = get(),
            sfdDao = get(),
            agencyDao = get(),
            personInfoDao = get(),
            api = get()
        )
    }

    single<ProfileRepository> {
        ProfileRepositoryMock(dispatcher = Dispatchers.IO, personInfoDao = get(), prefs = get())
    }

    single<CardsRepository> {
        CardsRepositoryMock(
            cardsDao = get(),
            coroutineDispatcher = Dispatchers.IO
        )
    }

    single {
        KsPrefs(androidApplication().applicationContext)
    }

    single(named("encryptedPrefs")) {
        val context = androidApplication().applicationContext

        val masterKey: MasterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()

        EncryptedSharedPreferences.create(
            context,
            "secured_shared_prefs",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    single<AppLockRepository> {
        AppLockRepositoryImpl(
            securedPreferences = get(named("encryptedPrefs")),
            context = androidApplication().applicationContext
        )
    }

    single<AccountRepository> {
        AccountRepositoryMock(
            coroutineDispatcher = Dispatchers.IO,
            cardsDao = get(),
            accountsDao = get(),
            agencyDao = get(),
            prefs = get(),
            api = get()
        )
    }

    single<TransactionRepository> {
        TransactionRepositoryMock(
            workManager = get(),
            transactionDao = get(),
            coroutineDispatcher = Dispatchers.IO,
            accountDao = get(),
            agencyDao = get(),
            prefs = get(),
            api = get()
        )
    }

}