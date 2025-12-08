package com.features.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.ExperimentalComposeUiApi
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.auth.presentation.model.AuthState
import com.features.auth.presentation.model.VerificationStatus
import com.features.ui.InputTextField
import com.features.ui.theme.MainTheme

@OptIn(ExperimentalComposeUiApi::class)
@Composable
fun CodeInputComponent(
    code: String,
    onCodeChanged: (String) -> Unit,
    verificationStatus: VerificationStatus
) {
    val focusRequesters = remember { List(4) { FocusRequester() } }

    LaunchedEffect(Unit) {
        focusRequesters[0].requestFocus()
    }

    val borderColor: Color? = when (verificationStatus) {
        VerificationStatus.NEUTRAL -> null
        VerificationStatus.SUCCESS -> MainTheme.colors.success
        VerificationStatus.ERROR -> MainTheme.colors.error
    }

    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(16.dp)
    ) {
        (0..3).forEach { index ->
            val currentChar = code.getOrNull(index)?.toString() ?: ""

            InputTextField(
                modifier = Modifier
                    .width(56.dp)
                    .focusRequester(focusRequesters[index])
                    .onPreviewKeyEvent { event ->
                        if (event.key == Key.Backspace && currentChar.isEmpty()) {
                            if (index > 0) {
                                focusRequesters[index - 1].requestFocus()
                            }
                            return@onPreviewKeyEvent true
                        }
                        false
                    },
                hintText = "",
                text = currentChar,
                onTextChange = { newChar ->
                    val newCode = code.toMutableList()

                    if (newChar.all { it.isDigit() }) {
                        if (newChar.isEmpty()) {

                            if (index < code.length) {
                                newCode.removeAt(index)
                            }
                        } else {
                            val digit = newChar.last()
                            if (index < code.length) {
                                newCode[index] = digit
                            } else {
                                newCode.add(digit)
                            }

                            if (index < 3) {
                                focusRequesters[index + 1].requestFocus()
                            }
                        }
                        onCodeChanged(newCode.joinToString("").take(4))
                    }
                },
                keyboardType = KeyboardType.Number,
                singleLine = true,
                maxLength = 1,
                textStyle = MainTheme.typography.auth.inputCode.copy(color = MainTheme.colors.secondary),
                borderColor = borderColor,
                isError = verificationStatus == VerificationStatus.ERROR,
            )
        }
    }
}
