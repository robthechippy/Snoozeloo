package com.rjprog.snoozeloo.edit_alarm.presentation

 sealed class EditAlarmScreenEvents {

     data class OnTitleChanged(val title: String) : EditAlarmScreenEvents()
     data class OnMinChanged(val minutes: String) : EditAlarmScreenEvents()
     data class OnHourChanged(val hours: String) : EditAlarmScreenEvents()
     data class SetTitleChangeFlag(val flag: Boolean = false) : EditAlarmScreenEvents()

     object OnDeleteAlarm : EditAlarmScreenEvents()
     object OnSaveAlarm : EditAlarmScreenEvents()

 }