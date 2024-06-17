package fr.utbm.bindoomobile.core

import android.app.Application
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.core.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.workmanager.koin.workManagerFactory
import org.koin.core.context.GlobalContext.startKoin

class BindooMobile: Application() {
    override fun onCreate() {
        super.onCreate()

        startKoin {
            androidLogger()
            androidContext(this@BindooMobile)
            workManagerFactory()
            modules(appModule)
        }

        setupNotifications()
    }

    private fun setupNotifications() {
        // Create notification channel
        // Create the NotificationChannel
        val name = getString(R.string.TRANSACTIONS_NOTIFICATION_CHANNEL_NAME)
        val id = getString(R.string.TRANSACTIONS_NOTIFICATION_CHANNEL_ID)

        val importance = NotificationManager.IMPORTANCE_HIGH
        val channel = NotificationChannel(id, name, importance)
        channel.setSound(null, null)

        val notificationManager = getSystemService(NOTIFICATION_SERVICE) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }
}