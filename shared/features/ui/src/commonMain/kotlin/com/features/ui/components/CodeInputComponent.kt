package com.features.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.features.ui.InputTextField

@Composable
fun CodeInputComponent() {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        InputTextField(
            modifier = Modifier.width(56.dp),
            hintText = "",
            keyboardType = KeyboardType.Phone,
            singleLine = true,
            maxLength = 1,
            onTextChange = {

            },
            text = ""
        )

        InputTextField(
            modifier = Modifier.width(56.dp),
            hintText = "",
            keyboardType = KeyboardType.Phone,
            singleLine = true,
            maxLength = 1,
            onTextChange = {

            },
            text = ""
        )

        InputTextField(
            modifier = Modifier.width(56.dp),
            hintText = "",
            keyboardType = KeyboardType.Phone,
            singleLine = true,
            maxLength = 1,
            onTextChange = {

            },
            text = ""
        )

        InputTextField(
            modifier = Modifier.width(56.dp),
            hintText = "",
            keyboardType = KeyboardType.Phone,
            singleLine = true,
            maxLength = 1,
            onTextChange = {

            },
            text = ""
        )
    }
}