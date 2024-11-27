package com.rjprog.snoozeloo.core.domain

import com.rjprog.snoozeloo.core.domain.models.Alarm
import kotlinx.coroutines.flow.Flow

interface AlarmRepositoryInterface {

    suspend fun insertAlarm(alarm: Alarm): Long

    suspend fun getAlarmById(id: Long): Alarm

    suspend fun deleteAlarmById(id: Long)

    fun getAllAlarms(): Flow<List<Alarm>>
}