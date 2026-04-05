package com.features.onboard.presentation.model

import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseState
import com.features.onboard.presentation.model.chat.ChatMessage
import com.features.onboard.presentation.model.state.OnboardingScreenState
import com.features.onboard.presentation.model.state.OnboardingStep

data class OnboardingState(
    override val error: Error? = null,
    override val isLoading: Boolean = false,
    val screenState: OnboardingScreenState = OnboardingScreenState.WELCOME,
    val curStep: OnboardingStep = OnboardingStep.ASK_NAME,
    val userName: String = "",
    val userAge: Int? = null,
    val chatMessages: List<ChatMessage> = emptyList(),
    val userTextInput: String = "",
    val isBrushingDialogVisible: Boolean = false,
    val brushingTimes: List<String> = listOf("9:00"),
) : BaseState(isLoading = isLoading, error = error)
