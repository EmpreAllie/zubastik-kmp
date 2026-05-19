package com.features.ai.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.features.ai.presentation.model.AiEvents
import com.features.ai.presentation.model.AiState
import com.features.ui.Res
import com.features.ui.ic_send_message
import com.features.ui.message
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AiSendMessageElement(
    state: AiState,
    onEvent: (AiEvents) -> Unit,
) {
    Row(
        modifier = Modifier.fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically
    ) {
        BasicTextField(
            modifier = Modifier
                .weight(1f)
                .clip(RoundedCornerShape(24.dp))
                .background(MainTheme.colors.containerBackground)
                .padding(horizontal = 16.dp, vertical = 10.dp),
            value = state.userInputText,
            onValueChange = {
                if (it.length <= 2000)
                    onEvent(AiEvents.OnUserTextInputChanged(it))
            },
            textStyle = MainTheme.typography.ai.messageContent.copy(
                color = MainTheme.colors.secondary
            ),
            cursorBrush = SolidColor(MainTheme.colors.secondary),
            decorationBox = { innerTextField ->
                Box {
                    if (state.userInputText.isEmpty()) {
                        Text(
                            text = stringResource(Res.string.message),
                            style = MainTheme.typography.ai.messageContent,
                            color = MainTheme.colors.secondary.copy(alpha = 0.4f)
                        )
                    }
                    innerTextField()
                }
            }
        )

        Spacer(modifier = Modifier.width(8.dp))

        IconButton(
            onClick = { onEvent(AiEvents.OnUserMessageSentToServer(state.userInputText)) },
            enabled = state.userInputText.isNotBlank()
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_send_message),
                contentDescription = null,
                tint =
                    if (state.userInputText.isNotBlank())
                        MainTheme.colors.secondary
                    else
                        MainTheme.colors.disabled
            )
        }
    }
}