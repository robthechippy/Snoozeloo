package com.rjprog.snoozeloo.core.data.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.rjprog.snoozeloo.core.domain.models.Alarm
import java.util.Calendar
import java.util.TimeZone


@Entity
data class AlarmDb(
    @PrimaryKey(autoGenerate = true)
    var id: Long = 0,
    var timeMillis: Long = 0,
    var title: String = "New",
    var enabled: Boolean = true
)

fun AlarmDb.toAlarm(): Alarm {
    var c:Calendar = Calendar.getInstance(TimeZone.getTimeZone("UTC"))
    c.timeInMillis = timeMillis
    c.timeZone = TimeZone.getDefault()

    return Alarm(
        id = id,
        timeMillis = timeMillis,
        hr24 = c.get(Calendar.HOUR_OF_DAY),
        min = c.get(Calendar.MINUTE),
        title = title,
        enabled = enabled
    )
}

fun Alarm.toAlarmDb(): AlarmDb {
    return AlarmDb(
        id = id,
        timeMillis = timeMillis,
        title = title,
        enabled = enabled,
    )
}