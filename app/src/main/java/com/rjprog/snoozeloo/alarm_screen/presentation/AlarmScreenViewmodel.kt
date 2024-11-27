package com.rjprog.snoozeloo.alarm_screen.presentation

import android.app.Activity
import android.app.Application
import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.rjprog.snoozeloo.core.domain.AlarmRepositoryInterface
import com.rjprog.snoozeloo.core.domain.AlarmSchedulerInterface
import org.koin.android.ext.koin.androidContext
import org.koin.core.parameter.parametersOf
import org.koin.dsl.koinApplication
import org.koin.java.KoinJavaComponent.inject

class AlarmScreenViewmodel(
    savedStateHandle: SavedStateHandle,
    private val repository: AlarmRepositoryInterface,
    private val alarmScheduler: AlarmSchedulerInterface,
    private val context: Context
): ViewModel() {
    var ringtone: Ringtone? = null
    var close = mutableStateOf(false)

    val alarmId = checkNotNull(savedStateHandle["EXTRA_ID"])
    val message = checkNotNull(savedStateHandle["EXTRA_MESSAGE"])
    val alarmTime = checkNotNull(savedStateHandle["EXTRA_TIME"])

    private val alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

    init {
        ringtone = RingtoneManager.getRingtone(context, alarmUri)
        ringtone?.play()
    }

    fun onEvent(event: AlarmScreenEvents) {
        when(event) {
            is AlarmScreenEvents.OnStopClicked -> {
                ringtone?.stop()
                close.value = true
            }
        }
    }

}