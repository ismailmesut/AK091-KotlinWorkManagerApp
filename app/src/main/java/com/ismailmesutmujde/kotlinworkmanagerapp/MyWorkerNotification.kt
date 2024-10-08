package com.ismailmesutmujde.kotlinworkmanagerapp

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.work.Worker
import androidx.work.WorkerParameters

class MyWorkerNotification (appContext: Context, workerParams: WorkerParameters) : Worker(appContext, workerParams) {
    override fun doWork(): Result {
        createNotification()
        return Result.success()
    }

    fun createNotification() {
        val builder: NotificationCompat.Builder
        val notificationManager = applicationContext.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val intent = Intent(applicationContext, MainActivity::class.java)

        /*
        val goToIntent = PendingIntent.getActivity(
            applicationContext,
            1,
            intent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )*/


        var goToIntent: PendingIntent? = null
        goToIntent = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.S) {
            PendingIntent.getActivity(applicationContext, 1,intent, PendingIntent.FLAG_MUTABLE)
        } else {
            PendingIntent.getActivity(applicationContext, 1,intent, PendingIntent.FLAG_IMMUTABLE)
        }

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val channelId = "channelId"
            val channelName = "channelName"
            val channelPromotion = "channelPromotion"
            val channelPriority = NotificationManager.IMPORTANCE_HIGH

            var channel : NotificationChannel? = notificationManager.getNotificationChannel(channelId)

            if (channel == null) {
                channel = NotificationChannel(channelId, channelName, channelPriority)
                channel.description = channelPromotion
                notificationManager.createNotificationChannel(channel)
            }

            builder = NotificationCompat.Builder(applicationContext)

            builder.setContentTitle("Title")
                .setContentText("Content")
                .setSmallIcon(R.drawable.image)
                .setContentIntent(goToIntent)
                .setAutoCancel(true)

        } else {
            builder = NotificationCompat.Builder(applicationContext)

            builder.setContentTitle("Title")
                .setContentText("Content")
                .setSmallIcon(R.drawable.image)
                .setContentIntent(goToIntent)
                .setAutoCancel(true)
                .priority = Notification.PRIORITY_HIGH
        }
        notificationManager.notify(1, builder.build())
    }
}