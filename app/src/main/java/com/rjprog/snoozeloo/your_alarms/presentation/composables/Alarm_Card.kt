package com.rjprog.snoozeloo.your_alarms.presentation.composables

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.SwitchDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.rjprog.snoozeloo.core.domain.models.Alarm
import com.rjprog.snoozeloo.ui.theme.SnoozelooTheme

@Composable
fun AlarmCard(
    alarm: Alarm,
    alarmIn: String,
    enabled: Boolean = true,
    enableClick: (Long) -> Unit,
    onCardClicked: (Long) -> Unit
) {
    Card(
        onClick = {
            onCardClicked(alarm.id)
        },
        modifier = Modifier
            .fillMaxWidth(),
        colors = CardDefaults.cardColors().copy(
            containerColor = Color.White
        )
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(16.dp)
            ) {
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically
                ) {
                    Text(
                        text = alarm.title,
                        style = MaterialTheme.typography.bodyLarge,
                        modifier = Modifier
                            .weight(1f)
                    )
                    Switch(
                        checked = enabled,
                        onCheckedChange = { enableClick(alarm.id) },
                        modifier = Modifier
                            .width(51.dp)
                            .height(30.dp),
                        colors = SwitchDefaults.colors().copy(
                            uncheckedBorderColor = MaterialTheme.colorScheme.surfaceDim,
                            uncheckedTrackColor = MaterialTheme.colorScheme.surfaceDim,
                            uncheckedThumbColor = Color.White,
                            uncheckedIconColor = Color.White,
                        )
                    )
                }
                Row(
                    verticalAlignment = Alignment.Bottom
                ) {
                    val hr =
                        when (alarm.hr24) {
                            0 -> "12"
                            in 1..9 -> "0${alarm.hr24}"
                            in 10..12 -> alarm.hr24.toString()
                            in 13..21 -> "0${alarm.hr24 - 12}"
                            else -> (alarm.hr24 - 12).toString()
                        }
                    val min =
                        when (alarm.min) {
                            0 -> "00"
                            in 1..9 -> "0${alarm.min}"
                            else -> alarm.min.toString()
                        }
                    Text(
                        text = "$hr:$min",
                        style = MaterialTheme.typography.headlineMedium
                    )
                    Text(
                        text = if (alarm.hr24 < 12) "AM" else "PM",
                        modifier = Modifier.padding(bottom = 4.dp, start = 4.dp),
                        style = MaterialTheme.typography.titleSmall
                    )
                }
                Text(
                    text = alarmIn,
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.secondary
                )
            }
        }
    }


}

@Preview
@Composable
fun AlarmCardPreview() {
    SnoozelooTheme {
        AlarmCard(
            alarm = Alarm(
                id = 1,
                title = "Work",
                timeMillis = 1L,
                hr24 = 0,
                min = 59
            ),
            alarmIn = "Alarm in 6h 30min",
            enableClick = {},
            onCardClicked = {}
        )
    }

}