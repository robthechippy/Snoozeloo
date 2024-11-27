@file:OptIn(ExperimentalMaterial3Api::class)

package com.rjprog.snoozeloo.edit_alarm.presentation

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.rjprog.snoozeloo.R
import com.rjprog.snoozeloo.edit_alarm.presentation.composables.AlarmEditBox
import com.rjprog.snoozeloo.edit_alarm.presentation.composables.EditTitlePopup
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar
import java.util.TimeZone

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditAlarmScreen(
    viewModel: EditScreenViewmodel = koinViewModel(),
    alarmSaved: () -> Unit
) {

    val screenState = viewModel.screenState.collectAsState()
    var currentTime by remember { mutableStateOf(Calendar.getInstance(TimeZone.getDefault())) }

    LaunchedEffect(true) {
        withContext(Dispatchers.Default) {
            while (true) {
                delay(1000L)
                currentTime = Calendar.getInstance(TimeZone.getDefault())
            }
        }
    }

    Scaffold(
        modifier = Modifier
            .fillMaxSize()
            .background(MaterialTheme.colorScheme.background),
        topBar = {
            TopAppBar(
                title = {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 24.dp)
                            .background(MaterialTheme.colorScheme.background),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(
                            onClick = {
                                viewModel.onEvent(EditAlarmScreenEvents.OnDeleteAlarm)
                                alarmSaved()
                            },
                            modifier = Modifier
                                .clip(RoundedCornerShape(8.dp))
                                .background(
                                    if (screenState.value.deleteEnabled) Color.Red else Color(
                                        0xFFE6E6E6
                                    )
                                )
                                .height(36.dp)
                                .width(36.dp),
                            enabled = screenState.value.deleteEnabled
                        ) {
                            Icon(
                                imageVector = Icons.Default.Close,
                                contentDescription = stringResource(R.string.close),
                                tint = MaterialTheme.colorScheme.surface
                            )
                        }
                        Spacer(modifier = Modifier.weight(1f))
                        Button(
                            onClick = {
                                viewModel.onEvent(EditAlarmScreenEvents.OnSaveAlarm)
                                alarmSaved()
                            },
                            modifier = Modifier
                                .padding(end = 16.dp),
                            enabled = screenState.value.saveEnabled
                        ) {
                            Text(
                                text = stringResource(R.string.save),
                                style = MaterialTheme.typography.bodyLarge,
                            )
                        }
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        }
    ) { innerPadding ->

        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 24.dp, bottom = 16.dp),
                colors = CardDefaults.cardColors().copy(
                    containerColor = Color.White
                )
            ) {
                Row(
                    modifier = Modifier
                        .padding(24.dp)
                        .fillMaxWidth(),
                    //verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center
                ) {
                    AlarmEditBox(
                        value = if (screenState.value.alarm.hr24 in 0..24) screenState.value.alarm.hr24.toString() else "",
                        onValueChange = { number ->
                            viewModel.onEvent(EditAlarmScreenEvents.OnHourChanged(number))
                        }
                    )

                    Text(
                        text = ":",
                        modifier = Modifier.padding(10.dp),
                        style = MaterialTheme.typography.titleMedium.copy(
                            color = MaterialTheme.colorScheme.secondary
                        )
                    )
                    AlarmEditBox(
                        value = if (screenState.value.alarm.min in 0..59) screenState.value.alarm.min.toString() else "",
                        onValueChange = { number ->
                            viewModel.onEvent(EditAlarmScreenEvents.OnMinChanged(number))
                        },
                        maxNumber = 59
                    )
                }
                if (screenState.value.saveEnabled) {
                    var alarmInMin = screenState.value.alarm.min - currentTime.get(Calendar.MINUTE)
                    if (alarmInMin < 0) {
                        alarmInMin += 60
                    }
                    var alarmInHr = screenState.value.alarm.hr24 - currentTime.get(Calendar.HOUR_OF_DAY)
                    if ((alarmInMin + currentTime.get(Calendar.MINUTE)) > 59) {
                        alarmInHr -= 1
                    }
                    if (alarmInHr < 0) {
                        alarmInHr += 24
                    }
                    Text(
                        text = "Alarm in ${alarmInHr}Hr ${alarmInMin}min",
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(bottom = 24.dp),
                        color = MaterialTheme.colorScheme.secondary,
                        style = MaterialTheme.typography.bodyMedium,
                        textAlign = TextAlign.Center
                    )
                }
            }
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(8.dp))
                    .background(Color.White)
                    .padding(16.dp)
            ) {
                Text(
                    text = stringResource(R.string.alarm_name),
                    style = MaterialTheme.typography.bodyLarge
                )
                Spacer(
                    modifier = Modifier
                        .weight(1f)
                )
                Text(
                    text = screenState.value.alarm.title,
                    modifier = Modifier
                        .clickable { viewModel.onEvent(EditAlarmScreenEvents.SetTitleChangeFlag(true)) },
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.secondary
                )
            }

        }
        if (screenState.value.editName) {
            EditTitlePopup(
                name = screenState.value.alarm.title,
                onSaveChanges = { newName ->
                    viewModel.onEvent(EditAlarmScreenEvents.OnTitleChanged(newName))
                    viewModel.onEvent(EditAlarmScreenEvents.SetTitleChangeFlag(false))
                },
                onDismiss = { viewModel.onEvent(EditAlarmScreenEvents.SetTitleChangeFlag(false)) }
            )
        }
    }
}

//@Preview
//@Composable
//fun EditAlarmScreenPreview() {
//    SnoozelooTheme {
//        KoinApplication(application = {
//            // your preview config here
//            modules(listOf(dbModule,repoModule, viewModelModule))
//        }) {
//        EditAlarmScreen(
//            alarmSaved = {}
//        ) }
//    }
//}