package fr.utbm.bindoomobile.core.di.data

import androidx.work.WorkManager
import fr.utbm.bindoomobile.data.workers.TransactionWorker
import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.workmanager.dsl.worker
import org.koin.dsl.module

val workerModule = module {
    single {
        WorkManager.getInstance(androidApplication().applicationContext)
    }

    worker {
        TransactionWorker(
            appContext = get(),
            workerParams = get(),
            transactionDao = get(),
            transactionNotificationHelper = get()
        )
    }
}