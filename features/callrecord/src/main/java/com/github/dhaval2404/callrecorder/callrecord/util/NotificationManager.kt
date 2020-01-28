package com.github.dhaval2404.callrecorder.callrecord.util

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.content.ContextWrapper
import android.graphics.Color
import android.os.Build
import androidx.core.app.NotificationCompat
import com.github.dhaval2404.callrecorder.callrecord.R

/**
 * Show notification with Android oreo support
 *
 * @author Dhaval Patel
 * @version 1.0
 * @since 03 April 2018
 */
class NotificationManager(ctx: Context) : ContextWrapper(ctx) {

    companion object {
        const val PRIMARY_CHANNEL_ID = "call_recoding"
        const val PRIMARY_CHANNEL = "Call Recoding"
    }

    /**
     * Get the notification manager.
     *
     * Utility method as this helper works with it a lot.
     *
     * @return The system service NotificationManager
     */
    private var manager = getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    /**
     * Registers notification channels, which can be used later by individual notifications.
     *
     * @param ctx The application context
     */
    init {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val chan1 = NotificationChannel(
                PRIMARY_CHANNEL_ID,
                PRIMARY_CHANNEL,
                NotificationManager.IMPORTANCE_DEFAULT
            ).apply {
                lightColor = Color.GREEN
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
            }
            manager.createNotificationChannel(chan1)
        }
    }

    /**
     * Get a notification of type 1
     *
     * Provide the builder rather than the notification it's self as useful for making notification
     * changes.
     *
     * @param title the title of the notification
     * @param body the body text for the notification
     * @return the builder as it keeps a reference to the notification (since API 24)
     */
    fun getNotification(
        title: String?,
        body: String?
    ): NotificationCompat.Builder { // Bitmap icon = BitmapFactory.decodeResource(getResources(), R.mipmap.ic_launcher);
        return NotificationCompat.Builder(applicationContext, PRIMARY_CHANNEL_ID)
            .setContentTitle(title)
            .setContentText(body)
            .setSmallIcon(R.drawable.round_mic_24)
            // .setLargeIcon(icon)
            .setAutoCancel(true)
            .setOngoing(true)
    }

    /**
     * Send a notification.
     *
     * @param id The ID of the notification
     * @param notification The notification object
     */
    fun notify(id: Int, notification: Notification?) {
        manager.notify(id, notification)
    }

    fun cancel(id: Int) {
        manager.cancel(id)
    }
}
