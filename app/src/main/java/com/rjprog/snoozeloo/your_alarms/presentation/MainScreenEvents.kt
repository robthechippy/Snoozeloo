package com.rjprog.snoozeloo.your_alarms.presentation

sealed class MainScreenEvents {

    data class ToggleAlarm(val alarmId: Long): MainScreenEvents()
}