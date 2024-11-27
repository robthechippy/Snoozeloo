package com.rjprog.snoozeloo.edit_alarm.Domain.Models

import com.rjprog.snoozeloo.core.domain.models.Alarm

data class EditScreenState(
    var alarmId: Long = 0,
    var alarm: Alarm = Alarm(),
    var editName: Boolean = false,
    var saveEnabled: Boolean = false,
    var deleteEnabled: Boolean = false
)
