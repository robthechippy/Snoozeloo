package com.rjprog.snoozeloo.your_alarms.presentation

import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FabPosition
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Snackbar
import androidx.compose.material3.SnackbarHostState
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.rjprog.snoozeloo.R
import com.rjprog.snoozeloo.core.domain.models.Alarm
import com.rjprog.snoozeloo.ui.theme.SnoozelooTheme
import com.rjprog.snoozeloo.your_alarms.presentation.composables.AlarmCard
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.withContext
import org.koin.androidx.compose.koinViewModel
import java.util.Calendar
import java.util.TimeZone

@Composable
fun MainScreen(
    onFabClick: (Long) -> Unit,
    onEditAlarm: (Long) -> Unit,
    viewModel: MainScreenViewmodel = koinViewModel()
) {
    val listItems by viewModel.alarmList.collectAsStateWithLifecycle()

    MainScreenActual(
        onFabClick = onFabClick,
        onEditAlarm = onEditAlarm,
        onEvent = viewModel::onEvent,
        listItems = listItems
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenActual(
    onFabClick: (Long) -> Unit,
    onEditAlarm: (Long) -> Unit,
    onEvent:(MainScreenEvents) -> Unit,
    listItems: List<Alarm>,
) {
    var currentTime by remember { mutableStateOf(Calendar.getInstance(TimeZone.getDefault())) }

//    val snackbarHostState = remember { SnackbarHostState() }

    LaunchedEffect(true) {
        withContext(Dispatchers.Default) {
            while (true) {
                delay(1000L)
                currentTime = Calendar.getInstance(TimeZone.getDefault())
            }
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Text(
                        text = stringResource(R.string.yourAlarms),
                        color = MaterialTheme.colorScheme.onSurface,
                        style = MaterialTheme.typography.titleSmall
                    )
                },
                colors = TopAppBarDefaults.topAppBarColors().copy(
                    containerColor = MaterialTheme.colorScheme.background
                )
            )
        },
        floatingActionButtonPosition = FabPosition.Center,
        floatingActionButton = {
            FloatingActionButton(
                onClick = { onFabClick(0L) },
                shape = RoundedCornerShape(30.dp),
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary,
                modifier = Modifier
                    .width(60.dp)
                    .height(60.dp)
            ) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = stringResource(R.string.add_alarm),
                    modifier = Modifier
                        .width(38.dp)
                        .height(38.dp),
                    tint = MaterialTheme.colorScheme.surface
                )
            }
        }

    ) { innerPadding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .padding(innerPadding)
                .padding(vertical = 16.dp, horizontal = 16.dp)
        ) {
            if (listItems.isEmpty()) {
                // Empty alarm list
                Column(
                    modifier = Modifier
                        .fillMaxSize(),
                    verticalArrangement = Arrangement.Center,
                ) {
                    Box(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(15.dp),
                        contentAlignment = Alignment.TopCenter
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.alarm_clock_shape),
                            contentDescription = null,
                            modifier = Modifier
                                .width(55.54.dp)
                                .height(55.54.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = stringResource(R.string.emptyAlarms),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 94.dp),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.bodyLarge.copy(
                                fontWeight = FontWeight(500)
                            ),
                            textAlign = TextAlign.Center
                        )
                    }
                }

            } else {
                LazyColumn(
                    modifier = Modifier
                        .fillMaxSize(),
//                        .padding(top = 24.dp),
                    state = rememberLazyListState(),
                    verticalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(
                        items = listItems.sortedBy { it.hr24 },
                        key = { it.id }
                    ) {
                        var alarmInMin = it.min - currentTime.get(Calendar.MINUTE)
                        if (alarmInMin < 0) {
                            alarmInMin += 60
                        }
                        var alarmInHr = it.hr24 - currentTime.get(Calendar.HOUR_OF_DAY)
                        if ((alarmInMin + currentTime.get(Calendar.MINUTE)) > 59) {
                            alarmInHr -= 1
                        }
                        if (alarmInHr < 0) {
                            alarmInHr += 24
                        }
                        AlarmCard(
                            alarm = it,
                            alarmIn = "Alarm in ${alarmInHr}Hr ${alarmInMin}min",
                            enabled = it.enabled,
                            enableClick = { onEvent(MainScreenEvents.ToggleAlarm(it)) },
                            onCardClicked = { id ->
                                onEditAlarm(id)
                            }
                        )

                    }
                }
            }
        }
    }
}

@Preview(showBackground = true, showSystemUi = true)
@Composable
fun MainScreenActualPreview() {
    SnoozelooTheme {
        MainScreenActual(
            onFabClick = {},
            onEditAlarm = {},
            onEvent = {},
            listItems = listOf()
        )
    }
}