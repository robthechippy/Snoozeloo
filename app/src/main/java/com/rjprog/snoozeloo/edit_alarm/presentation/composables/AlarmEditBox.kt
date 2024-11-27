package com.rjprog.snoozeloo.edit_alarm.presentation.composables

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.text.isDigitsOnly
import com.rjprog.snoozeloo.ui.theme.SnoozelooTheme

@Composable
fun AlarmEditBox(
    value: String = "",
    onValueChange: (String) -> Unit,
    maxNumber: Int = 24
) {
    var textFieldValueState by remember {
        mutableStateOf(TextFieldValue(text = ""))
    }
    var placeHolderText by remember { mutableStateOf(true) }
    when (value.length) {
        0 -> textFieldValueState = TextFieldValue(text = "")
        1 -> textFieldValueState = TextFieldValue(text = "0$value", selection = TextRange(value.length+1))
        else -> textFieldValueState = TextFieldValue(text = value, selection = TextRange(value.length+1))
    }

    TextField(
        value = textFieldValueState,
        onValueChange = { number ->
            if (number.text.isDigitsOnly()) {
                if (number.text == "") {
                    onValueChange("-1")
                } else {
                    if (number.text.toInt() <= maxNumber) {
                        onValueChange(number.text)
                    }
                }
            }
        },
        modifier = Modifier
            .width(128.dp)
            .clip(RoundedCornerShape(8.dp))
            .onFocusChanged { focusState ->
                if (focusState.isFocused) {
                    placeHolderText = false
                } else {
                    placeHolderText = true
                }
            },
        textStyle = MaterialTheme.typography.titleMedium.copy(
            color = MaterialTheme.colorScheme.primary,
            textAlign = TextAlign.Center
        ),
        placeholder = {
            Text(
                text = if (placeHolderText) "00" else "",
                modifier = Modifier
                    .padding(start = 10.dp),
                style = MaterialTheme.typography.titleMedium.copy(
                    color = MaterialTheme.colorScheme.secondary,
                    textAlign = TextAlign.Center
                )
            )
        },
        colors = TextFieldDefaults.colors().copy(
            focusedContainerColor = MaterialTheme.colorScheme.background,
            unfocusedContainerColor = MaterialTheme.colorScheme.background,
            focusedIndicatorColor = Color.Transparent,
            unfocusedIndicatorColor = Color.Transparent,
            disabledIndicatorColor = Color.Transparent
        ),
        singleLine = true,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Number
        )
    )
}

@Preview
@Composable
fun AlarmEditBoxPreview() {
    SnoozelooTheme {
        AlarmEditBox(
            onValueChange = {}
        )
    }
}