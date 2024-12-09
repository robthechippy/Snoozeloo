package com.rjprog.snoozeloo.core.services

import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.rjprog.snoozeloo.R
import com.rjprog.snoozeloo.alarm_screen.presentation.AlarmScreen

class AlarmReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context?, intent: Intent?) {
        val message = intent?.getStringExtra("EXTRA_MESSAGE") ?: "Alarm"
        val thisAlarmId = intent?.getStringExtra("EXTRA_ID") ?: "-1"
        val alarmTime = intent?.getStringExtra("EXTRA_TIME") ?: "00:00"
        val amPm =intent?.getStringExtra("EXTRA_AMPM") ?: "Am"
        val channelId = "alarm_id"
        val newIntent = Intent(context, AlarmScreen::class.java).apply {
            putExtra("EXTRA_ID", thisAlarmId)
            putExtra("EXTRA_MESSAGE", message)
            putExtra("EXTRA_TIME", alarmTime)
            putExtra("EXTRA_AMPM", amPm)
        }
        val pendingIntent =
            PendingIntent.getActivity(
                context,
                0,
                newIntent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        context?.let { ctx ->
            val notificationManager =
                ctx.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
            val builder = NotificationCompat.Builder(ctx, channelId)
                .setSmallIcon(R.drawable.alarm_clock_shape)
                .setContentTitle("Snoozeloo Alarm")
                .setContentText(message)
                .setChannelId(channelId)
                .setPriority(NotificationCompat.PRIORITY_MAX or Notification.FLAG_INSISTENT)
                .setFullScreenIntent(pendingIntent, true)
            notificationManager.notify(thisAlarmId.toInt(), builder.build())
        }
    }
}