package com.rjprog.snoozeloo.core.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.rjprog.snoozeloo.core.data.models.AlarmDb


@Database(entities = [AlarmDb::class], version = 1, exportSchema = false)
abstract class AppDatabase: RoomDatabase() {
    abstract fun alarmDao(): AlarmDbDao
}