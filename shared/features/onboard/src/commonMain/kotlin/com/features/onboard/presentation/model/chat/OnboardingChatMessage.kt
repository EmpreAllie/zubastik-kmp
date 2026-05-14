package com.features.onboard.presentation.model.chat

import com.features.onboard.presentation.model.state.OnboardingStep
import org.jetbrains.compose.resources.StringResource

data class OnboardingChatMessage(
    val id: Int,
    val textRes: StringResource? = null,
    val simpleText: String? = null,
    val formatArgs: List<Any> = emptyList(),
    val author: Author,
    val timeStamp: String,
    val createdOnStep: OnboardingStep? = null
) {
    companion object {
        fun user(
            id: Int,
            textRes: StringResource,
            formatArgs: List<Any> = emptyList(),
            timeStamp: String,
            step: OnboardingStep? = null
        ) = OnboardingChatMessage(
            id = id,
            textRes = textRes,
            formatArgs = formatArgs,
            author = Author.USER,
            timeStamp = timeStamp,
            createdOnStep = step
        )

        fun zub(
            id: Int,
            textRes: StringResource,
            formatArgs: List<Any> = emptyList(),
            timeStamp: String
        ) = OnboardingChatMessage(
            id = id,
            textRes = textRes,
            formatArgs = formatArgs,
            author = Author.ZUB,
            timeStamp = timeStamp
        )
    }
}