package com.features.onboard.presentation.model

import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseState
import org.jetbrains.compose.resources.StringResource

data class OnboardingState(
    override val error: Error? = null,
    override val isLoading: Boolean = false,

    val screenState: OnboardingScreenState = OnboardingScreenState.WELCOME,
    val curStep: OnboardingStep = OnboardingStep.ASK_NAME,
    val userName: String = "",
    val userAge: Int? = null,

    val chatMessages: List<ChatMessage> = emptyList()
) : BaseState(isLoading = isLoading, error = error)


enum class OnboardingScreenState {
    WELCOME,
    CHAT,
    TEETH
}

data class ChatMessage(
    val id: Int,
    val text: StringResource,
    val author: Author
)


enum class Author {
    ZUB,
    USER
}

