package com.rjprog.snoozeloo.edit_alarm.presentation.composables

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import androidx.compose.ui.window.DialogProperties
import com.rjprog.snoozeloo.R
import com.rjprog.snoozeloo.ui.theme.SnoozelooTheme

@Composable
fun EditTitlePopup(
    name: String,
    modifier: Modifier = Modifier,
    onSaveChanges: (String) -> Unit,
    onDismiss: () -> Unit
) {
    var newName by remember { mutableStateOf(name) }
    Dialog(
        onDismissRequest = { onDismiss() },
        properties = DialogProperties(
            dismissOnBackPress = true,
            dismissOnClickOutside = true
        )

    ) {
        Card(
            shape = RoundedCornerShape(8.dp),
            modifier = modifier
                .fillMaxWidth()
        ) {
            Text(
                text = stringResource(R.string.alarm_name),
                modifier = Modifier
                    .padding(top = 16.dp, start = 16.dp),
                style = MaterialTheme.typography.bodyLarge
            )
            OutlinedTextField(
                value = newName,
                onValueChange = { newName = it },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp, start = 16.dp, end = 16.dp),
                textStyle = MaterialTheme.typography.bodyMedium,
                colors = TextFieldDefaults.colors().copy(
                    focusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    unfocusedIndicatorColor = MaterialTheme.colorScheme.secondary,
                    disabledIndicatorColor = MaterialTheme.colorScheme.secondary,
                ),
                singleLine = true,
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
            ) {
                Spacer( modifier = Modifier.weight(1f))
                Button(
                    onClick = {onSaveChanges(newName)},
                    modifier = Modifier
                        .padding(top = 8.dp, end = 16.dp, bottom = 16.dp)
                ) {
                    Text(
                        text = stringResource(R.string.save)
                    )
                }
            }
        }
    }
}


@Preview
@Composable
private fun EditTitlePopupPreview() {
    SnoozelooTheme {
        EditTitlePopup(
            name = "Name",
            onSaveChanges = {},
            onDismiss = {}
        )
    }
}