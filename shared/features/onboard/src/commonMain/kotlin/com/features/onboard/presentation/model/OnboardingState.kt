package com.features.onboard.presentation.model

import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseState
import com.features.onboard.presentation.model.utils.Author
import com.features.onboard.presentation.model.utils.ChatMessage
import com.features.onboard.presentation.model.utils.OnboardingScreenState
import com.features.onboard.presentation.model.utils.OnboardingStep
import org.jetbrains.compose.resources.StringResource

data class OnboardingState(
    override val error: Error? = null,
    override val isLoading: Boolean = false,

    val screenState: OnboardingScreenState = OnboardingScreenState.WELCOME,
    val curStep: OnboardingStep = OnboardingStep.ASK_NAME,
    val userName: String = "",
    val userAge: Int? = null,

    val chatMessages: List<ChatMessage> = emptyList(),

    val userTextInput: String = ""
) : BaseState(isLoading = isLoading, error = error)