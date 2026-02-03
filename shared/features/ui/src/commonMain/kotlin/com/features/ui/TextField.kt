/*
package com.features.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.text.selection.LocalTextSelectionColors
import androidx.compose.foundation.text.selection.TextSelectionColors
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.focus.FocusDirection
import androidx.compose.ui.focus.onFocusChanged
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.TextRange
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
fun InputTextField( // TODO: Переписать под обычный InputTextField//OutlinedTextField
    modifier: Modifier = Modifier,
    text: String = "",
    hintText: String,
    onTextChange: (String) -> Unit = {},
    singleLine: Boolean = false,
    height: Dp = 56.dp,
    textSelection: Int = 0,
    errorText: String? = null,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    maxLength: Int = Int.MAX_VALUE,
    maxLines: Int = Int.MAX_VALUE,
    icon: DrawableResource? = null,
    onClickIcon: () -> Unit = {},
    onKeyboardNext: () -> Unit = {},
    onKeyboardDone: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val isFocused: MutableState<Boolean> = remember { mutableStateOf(false) }

    val borderColor = when {
        isError || !errorText.isNullOrBlank() -> MainTheme.colors.error
        isFocused.value -> MainTheme.colors.secondary
        else -> MainTheme.colors.disabledContent
    }

    Box(
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = borderColor,
                shape = RoundedCornerShape(12.dp)
            )
            .clip(RoundedCornerShape(12.dp))
            .background(MainTheme.colors.white)
            .heightIn(min = height),
        contentAlignment = Alignment.CenterStart,
    ) {

        val customTextSelectionColors = TextSelectionColors(
            handleColor = MainTheme.colors.thirdly,
            backgroundColor = MainTheme.colors.black.copy(0.2f)
        )
/*
        var textFieldValue by remember {
            mutableStateOf(
                TextFieldValue(
                    text = text,
                    selection = TextRange(textSelection)
                )
            )
        }

        LaunchedEffect(text) {
            textFieldValue =
                textFieldValue.copy(text = text, selection = TextRange(text.lastIndex + 1))
        }
*/
        // Если еще ничего не введено - то выводим текст-подсказку
        if (text.isEmpty()) {
            Text(
                modifier = Modifier
                    .padding(start = 20.dp)
                    .fillMaxWidth(),
                text = hintText,
                style = MainTheme.typography.main.hintText,
                color = (if (isError || !errorText.isNullOrEmpty()) MainTheme.colors.error else MainTheme.colors.black).copy(
                    alpha = 0.4f
                ),
            )
        }

        Row(
            modifier = Modifier
                .padding(start = 20.dp, end = 20.dp)
                .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                BasicTextField(
                    modifier = Modifier
                        .weight(1.0f)
                        .padding(
                            top = 0.dp,
                            start = 0.dp,
                            bottom = 0.dp,
                            end = if (icon != null) 16.dp else 8.dp
                        )
                        .onFocusChanged {
                            if (!isEnabled) {
                                focusManager.clearFocus()
                            }
                            isFocused.value = it.isFocused
                        },
                    enabled = isEnabled,
                    value = text,
                    maxLines = maxLines,
                    singleLine = singleLine,
                    keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                    keyboardActions = KeyboardActions(
                        onNext = {
                            onKeyboardNext()
                            focusManager.moveFocus(FocusDirection.Next)
                        },
                        onDone = {
                            focusManager.clearFocus()
                            onKeyboardDone()
                        }),
                    textStyle = MainTheme.typography.main.inputText.copy(color = MainTheme.colors.thirdly),
                    onValueChange = { newValue ->
                        if (isEnabled) {
                            onTextChange(newValue)
                        }
                    },
                    visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
                )
            }




            icon?.let {
                Icon(
                    modifier = Modifier
                        .size(24.dp)
                        .clickable {
                            onClickIcon()
                        },
                    painter = painterResource(it),
                    contentDescription = null,
                    tint = MainTheme.colors.thirdly,
                )
            }
        }
    }



    if (errorText != null) {
        Text(
            modifier = Modifier.padding(
                top = 0.dp,
                start = 20.dp,
                end = 20.dp,
            ),
            text = errorText,
            overflow = TextOverflow.Ellipsis,
            color = MainTheme.colors.error,
            style = MainTheme.typography.main.inputText,
            textAlign = TextAlign.Start
        )
    }
}






@Composable
fun DisabledTextField(
    modifier: Modifier = Modifier,
    text: String = "",
    hintText: String,
    height: Dp = 56.dp,
    textSelection: Int = 0,
    errorText: String? = null,
    isError: Boolean = false,
    isEnabled: Boolean = true,
    keyboardType: KeyboardType = KeyboardType.Text,
    trailingIcon: @Composable (() -> Unit)? = null,
    onClick: () -> Unit = {},
) {
    val focusManager = LocalFocusManager.current
    val isFocused: MutableState<Boolean> = remember { mutableStateOf(false) }

    val borderColor = when {
        isError || !errorText.isNullOrEmpty() -> MainTheme.colors.error
        isFocused.value -> MainTheme.colors.secondary
        else -> MainTheme.colors.secondary // disabledContent
    }


    Column(modifier = modifier) {
        /*
        if (text.isNotEmpty()) {

            Text(

                modifier = Modifier,
                    //.padding(bottom = 4.dp)
                    //.fillMaxWidth(),
                text = hintText,
                style = MainTheme.typography.main.disabledTextField,
                color = MainTheme.colors.thirdly.copy(alpha = 0.6f),
            )
        }

         */
        Box(
            modifier = Modifier
                .border(
                    width = 1.5.dp,
                    color = borderColor,
                    shape = RoundedCornerShape(12.dp)
                )
                .clip(RoundedCornerShape(12.dp))
                .background(MainTheme.colors.white)
                .heightIn(min = height)
                .clickable {
                    if (isEnabled) {
                        onClick()
                    }
                    isFocused.value = true
                },
            contentAlignment = Alignment.CenterStart,
        ) {

            val customTextSelectionColors = TextSelectionColors(
                handleColor = MainTheme.colors.thirdly,
                backgroundColor = MainTheme.colors.black.copy(0.2f)
            )

            var textFieldValue by remember {
                mutableStateOf(
                    TextFieldValue(
                        text = text,
                        selection = TextRange(textSelection)
                    )
                )
            }

            LaunchedEffect(text) {
                textFieldValue =
                    textFieldValue.copy(text = text, selection = TextRange(text.lastIndex + 1))
            }

            if (textFieldValue.text.isEmpty()) {
                Text(
                    modifier = Modifier
                        .padding(start = 24.dp)
                        .fillMaxWidth(),
                    text = hintText,
                    style = MainTheme.typography.main.disabledTextField,
                    color = if (isError || !errorText.isNullOrEmpty()) MainTheme.colors.error else MainTheme.colors.thirdly,
                )
            }

            Row(
                modifier = Modifier
                    .padding(start = 20.dp, end = 20.dp)
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                CompositionLocalProvider(LocalTextSelectionColors provides customTextSelectionColors) {
                    BasicTextField(
                        modifier = Modifier
                            .weight(1.0f)
                            .onFocusChanged {
                                isFocused.value = it.isFocused
                            }
                            .padding(
                                end = 16.dp
                            ),
                        enabled = false,
                        value = textFieldValue,
                        singleLine = true,
                        interactionSource = remember { MutableInteractionSource() },
                        keyboardOptions = KeyboardOptions(keyboardType = keyboardType),
                        keyboardActions = KeyboardActions(
                            onNext = {
                                focusManager.moveFocus(FocusDirection.Down)
                            },
                            onDone = {
                                focusManager.moveFocus(FocusDirection.Down)
                                focusManager.clearFocus()
                            }),
                        textStyle = MainTheme.typography.auth.countryCode.copy(color = MainTheme.colors.thirdly),
                        onValueChange = { _ -> },
                        visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None,
                    )
                }

                trailingIcon?.invoke()
            }
        }

        if (errorText != null) {
            Text(
                modifier = Modifier.padding(
                    top = 0.dp,
                    start = 20.dp,
                    end = 20.dp,
                ),
                text = errorText,
                overflow = TextOverflow.Ellipsis,
                color = MainTheme.colors.error,
                style = MainTheme.typography.main.inputText,
                textAlign = TextAlign.Start
            )
        }
    }
}

@Preview()
@Composable
internal fun TextField_Preview() {
    MainTheme {
        Column(
            modifier = Modifier
                .background(MainTheme.colors.primary)
                .padding(24.dp)
        ) {

            DisabledTextField(
                text = "",
                hintText = "12",
                trailingIcon = {
                    Icon(
                        modifier = Modifier
                            .size(24.dp),
                        painter = painterResource(Res.drawable.ic_back_arrow),
                        contentDescription = null,
                        tint = MainTheme.colors.thirdly.copy(alpha = 0.5f),
                    )
                }
            )
        }
    }
}

 */

package com.features.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
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
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
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
    borderColor: Color? = null
) {
    val focusManager = LocalFocusManager.current
    var isFocused by remember { mutableStateOf(false) }

    val calculatedBorderColor = when {
        isError || !errorText.isNullOrEmpty() -> MainTheme.colors.error
        isFocused -> MainTheme.colors.secondary
        else -> borderColor ?: MainTheme.colors.disabledContent
    }

    Box(
        modifier = modifier
            .border(
                width = 1.5.dp,
                color = borderColor ?: calculatedBorderColor,
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
                    .weight(1f)
                    .onFocusChanged { isFocused = it.isFocused },
                value = text,
                onValueChange = onTextChange,
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
                visualTransformation = if (keyboardType == KeyboardType.Password) PasswordVisualTransformation() else VisualTransformation.None
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
            val textToShow = if (text.isEmpty()) hintText else text
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
