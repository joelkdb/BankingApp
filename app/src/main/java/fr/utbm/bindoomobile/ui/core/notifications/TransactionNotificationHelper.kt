package fr.utbm.bindoomobile.ui.core.notifications

import android.Manifest
import android.app.Notification
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import androidx.compose.ui.graphics.toArgb
import androidx.core.app.ActivityCompat
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import fr.utbm.bindoomobile.MainActivity
import fr.utbm.bindoomobile.R
import fr.utbm.bindoomobile.domain.core.ErrorType
import fr.utbm.bindoomobile.domain.models.feature_account.MoneyAmount
import fr.utbm.bindoomobile.domain.models.feature_transactions.TransactionType
import fr.utbm.bindoomobile.ui.core.error.asUiTextError
import fr.utbm.bindoomobile.ui.feature_account.MoneyAmountUi
import fr.utbm.bindoomobile.ui.theme.primaryLight

class TransactionNotificationHelper(
    private val applicationContext: Context
) {
    fun successMessage(
        transactionType: TransactionType,
        amount: MoneyAmount,
        cardId: String,
    ): NotificationUi {
        return when (transactionType) {
            TransactionType.SEND -> {
                NotificationUi(
                    title = "Transaction confirmed!",
                    message = "You've sent ${MoneyAmountUi.mapFromDomain(amount).amountStr}",
                )
            }

            TransactionType.RECEIVE -> {
                NotificationUi(
                    title = "New incoming transaction!",
                    message = "You've received ${MoneyAmountUi.mapFromDomain(amount).amountStr}",
                )
            }

            TransactionType.TOP_UP -> {
                NotificationUi(
                    title = "Top up successful!",
                    message = "You've received ${MoneyAmountUi.mapFromDomain(amount).amountStr}",
                )
            }
        }
    }

    fun errorMessage(
        error: Throwable
    ): NotificationUi {
        val errorUi = ErrorType.fromThrowable(error).asUiTextError().asString(applicationContext)
        return NotificationUi(
            title = "Transaction failed",
            message = errorUi
        )
    }

    // Fore cases when tx launched in foreground service
    fun pending(): NotificationUi {
        return NotificationUi(
            title = "Operation in progress",
            message = "Please, wait a minute..."
        )
    }

    fun getNotification(notificationUi: NotificationUi): Notification {
        val notificationIntent = Intent(applicationContext, MainActivity::class.java)
        val mutabilityFlag = PendingIntent.FLAG_IMMUTABLE

        val pendingIntent =
            PendingIntent.getActivity(applicationContext, 0, notificationIntent, mutabilityFlag)

        return NotificationCompat.Builder(
            applicationContext,
            applicationContext.getString(R.string.TRANSACTIONS_NOTIFICATION_CHANNEL_ID)
        )
            .setContentTitle(notificationUi.title)
            .setContentText(notificationUi.message)
            .setSmallIcon(R.drawable.ic_bindoo_icon)
            .setColor(primaryLight.toArgb())
            .setContentIntent(pendingIntent)
            .build()
    }

    fun showNotification(notificationUi: NotificationUi) {
        val notification = getNotification(notificationUi)
        val notificationId = System.currentTimeMillis().toInt()

        if (ActivityCompat.checkSelfPermission(
                applicationContext,
                Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        ) {
            NotificationManagerCompat.from(applicationContext).notify(notificationId, notification)
        }
    }
}