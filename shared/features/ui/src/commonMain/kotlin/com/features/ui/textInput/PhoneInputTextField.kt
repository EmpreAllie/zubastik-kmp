package com.features.ui.textInput

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.getValue
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun InputTextField(
    modifier: Modifier = Modifier,
    text: String,
    onTextChange: (String) -> Unit,
    hintText: String,
    singleLine: Boolean = false,
    height: Dp = 56.dp,
    errorText: String? = null,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLines: Int = Int.MAX_VALUE,
    icon: DrawableResource? = null,
    onClickIcon: () -> Unit = {},
    onKeyboardNext: () -> Unit = {},
    onKeyboardDone: () -> Unit = {},
    maxLength: Int,
    textStyle: TextStyle,
    borderColor: Color? = null,
    visualTransformation: VisualTransformation = VisualTransformation.None
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .onFocusChanged { isFocused = it.isFocused }
                .border(
                    width = 1.5.dp,
                    color = when {
                        borderColor != null -> borderColor
                        (isError || !errorText.isNullOrEmpty()) && text.length == maxLength -> MainTheme.colors.error
                        isFocused -> MainTheme.colors.secondary
                        else -> MainTheme.colors.disabledContent
                    },
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(MainTheme.colors.white)
                .heightIn(min = height),
            contentAlignment = Alignment.CenterStart,
        ) {
            // Подсказка, если поле пустое
            if (text.isEmpty()) {
                Text(
                    modifier = Modifier.padding(horizontal = 20.dp),
                    text = hintText,
                    style = MainTheme.typography.main.hintText,
                    color = MainTheme.colors.black.copy(alpha = 0.4f),
                )
            }

            Row(
                modifier = Modifier.padding(horizontal = 20.dp),
                verticalAlignment = Alignment.CenterVertically
            ) {
                BasicTextField(
                    modifier = Modifier
                        .weight(1f),
                    value = text,
                    onValueChange = { newValue ->
                        if (newValue.length <= maxLength) {
                            onTextChange(newValue)
                        }
                    },
                    enabled = isEnabled,
                    textStyle = textStyle,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            onKeyboardNext()
                            focusManager.moveFocus(FocusDirection.Next)
                        },
                        onDone = {
                            focusManager.clearFocus()
                            onKeyboardDone()
                        }
                    ),
                    singleLine = singleLine,
                    maxLines = maxLines,
                    visualTransformation = visualTransformation
                )

                icon?.let {
                    Icon(
                        modifier = Modifier
                            .size(24.dp)
                            .clickable { onClickIcon() },
                        painter = painterResource(it),
                        contentDescription = null,
                        tint = MainTheme.colors.thirdly,
                    )
                }
            }
        }
        if (errorText != null) {
            // код для отображения errorText
            Text(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(start = 20.dp, top = 4.dp),
                text = errorText,
                color = MainTheme.colors.error,
                style = MainTheme.typography.auth.secondary
            )
        }
    }



}



@Composable
fun DisabledTextField(
    modifier: Modifier = Modifier,
    text: String,
    hintText: String,
    height: Dp = 56.dp,
    textStyle: TextStyle,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    Box(
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = MainTheme.colors.disabledContent, // Всегда неактивный цвет
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(MainTheme.colors.white)
            .heightIn(min = height)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.CenterStart
    ) {
        Row(
            modifier = Modifier
                .padding(horizontal = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            val textToShow = text.ifEmpty { hintText }
            val textColor = if (text.isEmpty()) MainTheme.colors.black.copy(alpha = 0.4f) else MainTheme.colors.thirdly

            Text(
                modifier = Modifier.weight(1f),
                text = textToShow,
                style = textStyle,
                color = textColor
            )

            trailingIcon?.invoke()
        }
    }
}
