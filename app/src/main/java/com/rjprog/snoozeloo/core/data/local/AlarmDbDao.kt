package com.rjprog.snoozeloo.core.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.rjprog.snoozeloo.core.data.models.AlarmDb
import kotlinx.coroutines.flow.Flow


@Dao
interface AlarmDbDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAlarmDb(alarmdb: AlarmDb): Long

    @Query("SELECT * FROM AlarmDb WHERE id = :id")
    suspend fun getAlarmDbById(id: Long): AlarmDb

    @Query("DELETE FROM AlarmDb WHERE id = :id")
    suspend fun deleteAlarmDbById(id: Long)

    @Query("SELECT * FROM AlarmDb")
    fun getAllAlarmDbs(): Flow<List<AlarmDb>>

}