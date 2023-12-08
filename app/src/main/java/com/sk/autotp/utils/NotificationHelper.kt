package com.sk.autotp.utils

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import androidx.core.app.NotificationCompat
import com.sk.autotp.R

object NotificationHelper {

    private const val CHANNEL_NAME = "AutOTP Notification"
    private const val CHANNEL_ID = "AutOTP_notification_channel"

    fun createNotificationChannel(context: Context) {
        val channel = NotificationChannel(
            CHANNEL_ID,
            CHANNEL_NAME,
            NotificationManager.IMPORTANCE_HIGH
        )
        val notificationManager = context.getSystemService(
            Context.NOTIFICATION_SERVICE
        ) as NotificationManager
        notificationManager.createNotificationChannel(channel)
    }

    fun createNotification(
        context: Context,
        title: String = context.getString(R.string.app_name),
        text: String,
        priority: Int = NotificationCompat.PRIORITY_HIGH,
    ): Notification {
        val builder = NotificationCompat.Builder(context, CHANNEL_ID)
            .setSmallIcon(R.drawable.ic_autotp)
            .setContentTitle(title)
            .setContentText(text)
            .setPriority(priority)

        return builder.build()
    }

}