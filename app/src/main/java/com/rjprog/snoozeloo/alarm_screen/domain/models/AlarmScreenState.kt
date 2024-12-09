package com.rjprog.snoozeloo.alarm_screen.domain.models

import com.rjprog.snoozeloo.core.domain.models.Alarm

data class AlarmScreenState(
    val alarmId: String = "-1",
    val message: String = "Wake up!",
    val alarmTime: String = "00:00",
    val amPm: String = "Am",
    val alarm: Alarm = Alarm(),
    val isSnooze: Boolean = false
    )
