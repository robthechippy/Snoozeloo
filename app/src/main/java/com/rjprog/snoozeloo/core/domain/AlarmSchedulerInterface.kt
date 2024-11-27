package com.rjprog.snoozeloo.core.domain

import com.rjprog.snoozeloo.core.domain.models.Alarm

interface AlarmSchedulerInterface {
    fun ScheduleAlarm(alarm: Alarm)
    fun CancelAlarm(alarm: Alarm)
}