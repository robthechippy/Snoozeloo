package com.rjprog.snoozeloo.alarm_screen.presentation

import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.bundle.Bundle
import com.rjprog.snoozeloo.R
import com.rjprog.snoozeloo.core.services.turnScreenOffAndKeyguardOn
import com.rjprog.snoozeloo.core.services.turnScreenOnAndKeyguardOff
import com.rjprog.snoozeloo.ui.theme.SnoozelooTheme
import org.koin.androidx.compose.KoinAndroidContext
import org.koin.androidx.compose.koinViewModel


class AlarmScreen() : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            SnoozelooTheme {
                KoinAndroidContext {
//                    startRingtone()
                    MainAlarmScreen()

                }
            }
        }
        turnScreenOnAndKeyguardOff()

    }

    override fun onDestroy() {
        super.onDestroy()
        turnScreenOffAndKeyguardOn()
    }


    @Composable
    fun MainAlarmScreen(
        viewmodel: AlarmScreenViewmodel = koinViewModel(),
    ) {
        if (viewmodel.close.value) finishAndRemoveTask()

        Scaffold(
        ) { innerPadding ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(innerPadding)
                    .padding(16.dp),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    modifier = Modifier
                        .fillMaxWidth(),
                    contentAlignment = Alignment.Center
                ) {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth(),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.alarm_clock_shape),
                            contentDescription = null,
                            modifier = Modifier
                                .width(62.dp)
                                .height(62.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = viewmodel.alarmTime.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 24.dp),
                            color = MaterialTheme.colorScheme.primary,
                            style = MaterialTheme.typography.titleLarge,
                            textAlign = TextAlign.Center
                        )
                        Text(
                            text = viewmodel.message.toString(),
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(top = 10.dp),
                            color = MaterialTheme.colorScheme.onSurface,
                            style = MaterialTheme.typography.titleSmall.copy(
                                fontWeight = FontWeight(600)
                            ),
                            textAlign = TextAlign.Center
                        )
                        Button(
                            onClick = { viewmodel.onEvent(AlarmScreenEvents.OnStopClicked) },
                            modifier = Modifier
                                .padding(top = 24.dp)
                        ) {
                            Text(
                                text = stringResource(R.string.stop),
                                modifier = Modifier
                                    .padding(start = 50.dp, end = 50.dp),
                                style = MaterialTheme.typography.titleSmall.copy(
                                    fontWeight = FontWeight(600)
                                )
                            )
                        }
                    }
                }

            }
        }
    }
}
