package com.rjprog.snoozeloo.core.data.local

import com.rjprog.snoozeloo.core.data.models.toAlarm
import com.rjprog.snoozeloo.core.data.models.toAlarmDb
import com.rjprog.snoozeloo.core.domain.models.Alarm
import com.rjprog.snoozeloo.core.domain.AlarmRepositoryInterface
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

class AlarmRepositoryLocal( private val alarmDao: AlarmDbDao): AlarmRepositoryInterface {

    override suspend fun insertAlarm(alarm: Alarm): Long {
        return alarmDao.insertAlarmDb(alarm.toAlarmDb())
    }

    override suspend fun getAlarmById(id: Long): Alarm {
        return alarmDao.getAlarmDbById(id).toAlarm()
    }

    override suspend fun deleteAlarmById(id: Long) = alarmDao.deleteAlarmDbById(id)

    override fun getAllAlarms(): Flow<List<Alarm>> {
        return alarmDao.getAllAlarmDbs().map { it ->
            it.map {
                it.toAlarm()
            }
        }
    }
}