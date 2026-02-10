package com.features.onboard.ui.components.content.inner

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.onboard.presentation.model.utils.Author
import com.features.onboard.presentation.model.utils.ChatMessage
import com.features.onboard.presentation.model.utils.OnboardingStep
import com.features.ui.InlineMessageTextField
import com.features.ui.Res
import com.features.ui.hintYourName
import com.features.ui.me
import com.features.ui.myNameIs
import com.features.ui.theme.MainTheme
import com.features.ui.user
import com.features.ui.zub
import com.features.ui.zubastik
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessageItem(
    message: ChatMessage,
    currentStep: OnboardingStep,
    userTextInput: String,
    onEvent: (OnboardingEvents) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp),
        horizontalArrangement =
            if (message.author == Author.ZUB)
                Arrangement.Start
            else
                Arrangement.End
    ) {

        if (message.author == Author.ZUB) {
            Image(
                modifier = Modifier
                    .size(40.dp),
                painter = painterResource(Res.drawable.zub),
                contentDescription = "Zubastik in the chat",
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier = Modifier
                .weight(1f, fill = false)
                .background(
                    color = MainTheme.colors.white,
                    shape = RoundedCornerShape(16.dp)
                )
                .padding(horizontal = 16.dp, vertical = 12.dp)
        ) {

            // Имя отправителя
            Text(
                text =
                    if (message.author == Author.ZUB)
                        stringResource(Res.string.zubastik)
                    else
                        stringResource(Res.string.me),
                color = MainTheme.colors.secondary,
                style = MainTheme.typography.message.author
            )

            Spacer(modifier = Modifier.height(4.dp))

            if (message.author == Author.USER && currentStep == OnboardingStep.ASK_NAME) {
                InlineMessageTextField(
                    text = userTextInput,
                    onTextChange = { newText ->
                        onEvent(OnboardingEvents.OnTextInputChanged(newText))
                    },
                    hint = stringResource(Res.string.hintYourName),
                    prefix = stringResource(Res.string.myNameIs)
                )
            }
            else {
                Text(
                    text = message.textRes?.let { stringResource(it) } ?: message.simpleText ?: "",
                    color = MainTheme.colors.secondary,
                    style = MainTheme.typography.message.text
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                modifier = Modifier
                    .align(Alignment.End),
                text = message.timeStamp,
                color = MainTheme.colors.secondary,
                style = MainTheme.typography.message.time
            )
        }

        if (message.author == Author.USER) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier
                    .size(40.dp),
                painter = painterResource(Res.drawable.user),
                contentDescription = "User in the chat",
            )
        }
    }
}