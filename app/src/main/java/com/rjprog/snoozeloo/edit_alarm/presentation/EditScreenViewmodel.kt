package com.rjprog.snoozeloo.edit_alarm.presentation

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.rjprog.snoozeloo.core.domain.AlarmRepositoryInterface
import com.rjprog.snoozeloo.core.domain.AlarmSchedulerInterface
import com.rjprog.snoozeloo.core.domain.CheckIfTimePast
import com.rjprog.snoozeloo.edit_alarm.Domain.Models.EditScreenState
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

class EditScreenViewmodel(
    savedStateHandle: SavedStateHandle,
    private val repository: AlarmRepositoryInterface,
    private val alarmScheduler: AlarmSchedulerInterface
) : ViewModel() {

    var alarmId = checkNotNull(savedStateHandle.get<Long>("alarmId"))
    private val _state = MutableStateFlow(EditScreenState())
    val screenState = _state
        .onStart {
            loadData()
        }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = _state.value
        )

    fun onEvent(event: EditAlarmScreenEvents) {
        when (event) {
            is EditAlarmScreenEvents.OnHourChanged -> {
                var newHour = 0
                if (event.hours == "-1") {
                    _state.value = screenState.value.copy(
                        saveEnabled = false
                    )
                }
                if (event.hours != "") newHour = event.hours.toInt()
                _state.value = screenState.value.copy(
                    alarm = screenState.value.alarm.copy(hr24 = newHour)
                )
                if (screenState.value.alarm.hr24 != -1 && screenState.value.alarm.min == -1)
                    _state.value = screenState.value.copy(
                        alarm = screenState.value.alarm.copy(
                            min = 0
                        )
                    )
                if (event.hours != "-1") {
                    _state.value = screenState.value.copy(
                        saveEnabled = true
                    )
                    val alarmTime = CheckIfTimePast(screenState.value.alarm)

                    _state.value = screenState.value.copy(
                        alarm = screenState.value.alarm.copy(
                            timeMillis = alarmTime.timeInMillis
                        )
                    )
                }
            }

            is EditAlarmScreenEvents.OnMinChanged -> {
                var newMinute = 0
                if (event.minutes == "-1") {
                    _state.value = screenState.value.copy(
                        saveEnabled = false
                    )
                }
                if (event.minutes != "") newMinute = event.minutes.toInt()
                _state.value = screenState.value.copy(
                    alarm = screenState.value.alarm.copy(min = newMinute)
                )
                if (screenState.value.alarm.min != -1 && screenState.value.alarm.hr24 == -1)
                    _state.value = screenState.value.copy(
                        alarm = screenState.value.alarm.copy(
                            hr24 = 0
                        )
                    )
                if (event.minutes != "-1") {
                    _state.value = screenState.value.copy(
                        saveEnabled = true
                    )
                    val alarmTime = CheckIfTimePast(screenState.value.alarm)

                    _state.value = screenState.value.copy(
                        alarm = screenState.value.alarm.copy(
                            timeMillis = alarmTime.timeInMillis
                        )
                    )
                }
            }

            EditAlarmScreenEvents.OnSaveAlarm -> {
                val alarmTime = CheckIfTimePast(screenState.value.alarm)

                _state.value = screenState.value.copy(
                    alarm = screenState.value.alarm.copy(
                        timeMillis = alarmTime.timeInMillis
                    )
                )
                viewModelScope.launch {
                    val id = repository.insertAlarm(screenState.value.alarm)
                    if (id != alarmId)
                        _state.value = screenState.value.copy(
                            alarm = screenState.value.alarm.copy(
                                id = id
                            )
                        )
                    alarmScheduler.ScheduleAlarm(screenState.value.alarm)
                }
            }

            is EditAlarmScreenEvents.SetTitleChangeFlag -> {
                _state.value = screenState.value.copy(
                    editName = event.flag
                )
            }

            is EditAlarmScreenEvents.OnTitleChanged -> {
                _state.value = screenState.value.copy(
                    alarm = screenState.value.alarm.copy(title = event.title)
                )
                if (screenState.value.alarm.id != 0L) {
                    _state.value = screenState.value.copy(
                        saveEnabled = true
                    )
                }
            }

            EditAlarmScreenEvents.OnDeleteAlarm -> {
                viewModelScope.launch {
                    repository.deleteAlarmById(screenState.value.alarm.id)
                }
            }
        }
    }

    private fun loadData() {
        if (alarmId != 0L) {
            viewModelScope.launch {
                val alarm = repository.getAlarmById(alarmId)
                _state.value = screenState.value.copy(
                    alarm = alarm
                )
            }
            _state.value = screenState.value.copy(
                deleteEnabled = true)
        }
    }
}