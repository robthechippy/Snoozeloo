package com.rjprog.snoozeloo.your_alarms.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rjprog.snoozeloo.core.domain.AlarmRepositoryInterface
import com.rjprog.snoozeloo.core.domain.AlarmSchedulerInterface
import com.rjprog.snoozeloo.core.domain.CheckIfTimePast
import com.rjprog.snoozeloo.core.domain.models.Alarm
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class MainScreenViewmodel(
    private val repository: AlarmRepositoryInterface,
    private val alarmScheduler: AlarmSchedulerInterface
): ViewModel() {

    private val _alarmList = MutableStateFlow(repository.getAllAlarms()
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = listOf()
        ))
    val alarmList: StateFlow<List<Alarm>>
        get() = _alarmList.value

    fun onEvent(event: MainScreenEvents) {
        when(event) {
            is MainScreenEvents.ToggleAlarm -> {
                var thisAlarm = alarmList.value.find { it.id == event.alarmId }
                if (thisAlarm != null) {
                    /* Cancel the alarm before changing the enabled state, else the hashcode
                    * will be different, and the alarm will not be cancelled */
                    if (thisAlarm.enabled) {
                        alarmScheduler.CancelAlarm(thisAlarm)
                    } else {
                        alarmScheduler.ScheduleAlarm(thisAlarm)
                    }
                    thisAlarm = thisAlarm.copy(enabled = !thisAlarm.enabled)
                    val alarmTime = CheckIfTimePast(thisAlarm)
                    thisAlarm = thisAlarm.copy(timeMillis = alarmTime.timeInMillis)
                    viewModelScope.launch {
                        repository.insertAlarm(thisAlarm)
                    }
                }
            }
        }
    }

}