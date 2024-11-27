package com.rjprog.snoozeloo.alarm_screen.presentation

sealed class AlarmScreenEvents {
    data object OnStopClicked: AlarmScreenEvents()
}