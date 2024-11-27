package com.rjprog.snoozeloo.alarm_screen.domain.models

import com.rjprog.snoozeloo.core.domain.models.Alarm

data class AlarmScreenState(
    var alarm: Alarm = Alarm(),
    var isSnooze: Boolean = false
    )
