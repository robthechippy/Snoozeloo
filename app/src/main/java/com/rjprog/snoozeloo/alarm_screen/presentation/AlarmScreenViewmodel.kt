package com.rjprog.snoozeloo.alarm_screen.presentation

import android.content.Context
import android.media.Ringtone
import android.media.RingtoneManager
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rjprog.snoozeloo.alarm_screen.domain.models.AlarmScreenState
import com.rjprog.snoozeloo.core.domain.AlarmRepositoryInterface
import com.rjprog.snoozeloo.core.domain.AlarmSchedulerInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn

class AlarmScreenViewmodel(
    savedStateHandle: SavedStateHandle,
    private val repository: AlarmRepositoryInterface,
    private val alarmScheduler: AlarmSchedulerInterface,
    context: Context
): ViewModel() {
    private var ringtone: Ringtone? = null
    private var alarmUri = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)

    var close = mutableStateOf(false)

    private val _state = MutableStateFlow(AlarmScreenState())
    val screenState = _state
        .onStart {
            _state.value = _state.value.copy(
                alarmId = checkNotNull(savedStateHandle["EXTRA_ID"] ?: "-1"),
                message = checkNotNull(savedStateHandle["EXTRA_MESSAGE"] ?: "Alarm!"),
                alarmTime = checkNotNull(savedStateHandle["EXTRA_TIME"] ?: "00:00"),
                amPm = checkNotNull(savedStateHandle["EXTRA_AMPM"] ?: "Am"),
            )
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )


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