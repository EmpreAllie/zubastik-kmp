package com.features.onboard.ui.components.content.inner.screens.chat

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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import com.features.onboard.presentation.model.chat.Author
import com.features.onboard.presentation.model.chat.ChatMessage
import com.features.onboard.presentation.model.state.OnboardingStep
import com.features.ui.InlineMessageTextField
import com.features.ui.Res
import com.features.ui.hintYourAge
import com.features.ui.hintYourName
import com.features.ui.iAm
import com.features.ui.me
import com.features.ui.myNameIs
import com.features.ui.theme.MainTheme
import com.features.ui.user
import com.features.ui.yearsOld
import com.features.ui.zub
import com.features.ui.zubastik
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun MessageItem(
    message: ChatMessage,
    curStep: OnboardingStep,
    userTextInput: String,
    userName: String,
    userAge: Int?,
    onTextChanged: (String) -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
        horizontalArrangement =
            if (message.author == Author.ZUB) {
                Arrangement.Start
            } else {
                Arrangement.End
            },
    ) {
        // Иконка зубастика
        if (message.author == Author.ZUB) {
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(Res.drawable.zub),
                contentDescription = "Zubastik in the chat",
            )
            Spacer(modifier = Modifier.width(8.dp))
        }

        Column(
            modifier =
                Modifier
                    .weight(1f, fill = false)
                    .background(
                        color = MainTheme.colors.white,
                        shape = RoundedCornerShape(16.dp),
                    ).padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment =
                if (message.author == Author.ZUB) {
                    Alignment.Start
                } else {
                    Alignment.End
                },
        ) {
            // Имя отправителя
            Text(
                text =
                    if (message.author == Author.ZUB) {
                        stringResource(Res.string.zubastik)
                    } else {
                        stringResource(Res.string.me)
                    },
                color = MainTheme.colors.secondary,
                style = MainTheme.typography.message.author,
            )

            Spacer(modifier = Modifier.height(4.dp))

            // Содержимое сообщения
            if (message.author == Author.USER && message.createdOnStep != null) {
                InlineMessageTextField(
                    text =
                        when (message.createdOnStep) {
                            OnboardingStep.ASK_NAME -> {
                                if (curStep == OnboardingStep.ASK_NAME) {
                                    userTextInput
                                } else {
                                    userName
                                }
                            }

                            OnboardingStep.ASK_AGE -> {
                                if (curStep == OnboardingStep.ASK_AGE) {
                                    userTextInput
                                } else {
                                    (userAge?.toString() ?: "")
                                }
                            }

                            else -> {
                                ""
                            }
                        },
                    onTextChange = onTextChanged,
                    hint =
                        when (message.createdOnStep) {
                            OnboardingStep.ASK_NAME -> stringResource(Res.string.hintYourName)
                            OnboardingStep.ASK_AGE -> stringResource(Res.string.hintYourAge)
                            else -> ""
                        },
                    prefix =
                        when (message.createdOnStep) {
                            OnboardingStep.ASK_NAME -> stringResource(Res.string.myNameIs)
                            OnboardingStep.ASK_AGE -> stringResource(Res.string.iAm)
                            else -> null
                        },
                    postfix =
                        when (message.createdOnStep) {
                            OnboardingStep.ASK_AGE -> stringResource(Res.string.yearsOld)
                            else -> null
                        },
                    enabled = curStep == message.createdOnStep,
                )
            } else {
                Text(
                    text =
                        buildAnnotatedString {
                            message.textRes?.let { res ->
                                val finalArgs =
                                    message.formatArgs
                                        .map { arg ->
                                            if (arg is StringResource) {
                                                stringResource(arg)
                                            } else {
                                                arg
                                            }
                                        }.toTypedArray()

                                append(
                                    stringResource(
                                        resource = res,
                                        formatArgs = finalArgs,
                                    ),
                                )
                            }

                            message.simpleText?.let {
                                withStyle(
                                    style =
                                        SpanStyle(
                                            fontWeight = FontWeight.Bold,
                                            textDecoration = TextDecoration.Underline,
                                        ),
                                ) {
                                    append(it)
                                }
                            }
                        },
                    color = MainTheme.colors.secondary,
                    style = MainTheme.typography.message.text,
                )
            }

            Spacer(modifier = Modifier.height(4.dp))

            // Время отправки
            Text(
                modifier =
                    if (message.author == Author.ZUB) {
                        Modifier.align(Alignment.End)
                    } else {
                        Modifier.align(Alignment.Start)
                    },
                text = message.timeStamp,
                color = MainTheme.colors.black.copy(alpha = 0.4f),
                style = MainTheme.typography.message.time,
            )
        }

        // Иконка юзера
        if (message.author == Author.USER) {
            Spacer(modifier = Modifier.width(8.dp))
            Image(
                modifier = Modifier.size(40.dp),
                painter = painterResource(Res.drawable.user),
                contentDescription = "User in the chat",
            )
        }
    }
}
