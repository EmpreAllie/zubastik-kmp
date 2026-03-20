package com.features.ui.textInput

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.features.ui.theme.MainTheme

@Composable
fun NotesTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    hintText: String,
    maxLength: Int = 150,
    textStyle: TextStyle = MainTheme.typography.teeth.inputTextField,
    minHeight: Dp = 80.dp
) {
    var isFocused by remember { mutableStateOf(false) }

    BasicTextField(
        value = text,
        onValueChange = {
            if (it.length <= maxLength)
                onTextChange(it)
        },
        modifier = Modifier
            .fillMaxWidth()
            .heightIn(min = minHeight)
            .onFocusChanged { isFocused = it.isFocused }
            .border(
                width = 1.dp,
                color = if (isFocused) MainTheme.colors.secondary else MainTheme.colors.lightGray,
                shape = RoundedCornerShape(10.dp)
            )
            .padding(12.dp),
        textStyle = textStyle.copy(
            color = if (isFocused) MainTheme.colors.secondary else MainTheme.colors.lightGray
        ),
        decorationBox = { innerTextField ->
            Box {
                if (text.isEmpty()) {
                    Text(
                        text = hintText,
                        style = textStyle,
                        color = MainTheme.colors.lightGray
                    )
                }
                innerTextField()
            }
        }
    )
}