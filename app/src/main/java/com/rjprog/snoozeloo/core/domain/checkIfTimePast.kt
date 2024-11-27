package com.rjprog.snoozeloo.core.domain

import com.rjprog.snoozeloo.core.domain.models.Alarm
import java.util.Calendar
import java.util.TimeZone

fun CheckIfTimePast(alarm: Alarm): Calendar{

    val currentTime = Calendar.getInstance(TimeZone.getDefault())
    val alarmTime = Calendar.getInstance(TimeZone.getDefault())

    alarmTime.set(Calendar.HOUR_OF_DAY, alarm.hr24)
    alarmTime.set(Calendar.MINUTE, alarm.min)
    alarmTime.set(Calendar.SECOND, 0)
    if (alarmTime.before(currentTime)) {
        alarmTime.add(Calendar.DATE, 1)
    }
    return alarmTime
}