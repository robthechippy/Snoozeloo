package com.rjprog.snoozeloo.core.services

import android.annotation.SuppressLint
import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.rjprog.snoozeloo.core.domain.AlarmSchedulerInterface
import com.rjprog.snoozeloo.core.domain.models.Alarm

class AlarmSchedulerImpl(
    private val context: Context,
) : AlarmSchedulerInterface {

    private val alarmManager = context.getSystemService(AlarmManager::class.java)

    @SuppressLint("MissingPermission")
    override fun ScheduleAlarm(alarm: Alarm) {
        val hr = if (alarm.hr24 < 10) "0${alarm.hr24}" else alarm.hr24.toString()
        val min = if (alarm.min < 10) "0${alarm.min}" else alarm.min.toString()
        val intent = Intent(context, AlarmReceiver::class.java).apply {
            putExtra("EXTRA_MESSAGE", alarm.title)
            putExtra("EXTRA_ID", alarm.id.toString())
            putExtra("EXTRA_TIME", "${hr}:${min}")
        }
        val alarmTime = alarm.timeMillis
        alarmManager.setExactAndAllowWhileIdle(
            AlarmManager.RTC_WAKEUP,
            alarmTime,
            PendingIntent.getBroadcast(
                /* context = */ context,
                /* requestCode = */ alarm.hashCode(),
                /* intent = */ intent,
                /* flags = */ PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }

    override fun CancelAlarm(alarm: Alarm) {
        alarmManager.cancel(
            PendingIntent.getBroadcast(
                context,
                alarm.hashCode(),
                Intent(context, AlarmReceiver::class.java),
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        )
    }
}