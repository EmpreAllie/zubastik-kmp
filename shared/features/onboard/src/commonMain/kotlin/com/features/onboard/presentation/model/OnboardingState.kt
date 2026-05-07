package com.features.onboard.presentation.model

import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseState
import com.features.onboard.presentation.model.chat.ChatMessage
import com.features.onboard.presentation.model.state.OnboardingScreenState
import com.features.onboard.presentation.model.state.OnboardingStep

data class OnboardingState(
    override val error: Error? = null,
    override val isLoading: Boolean = false,
    val isBrushingDialogVisible: Boolean = false,
    val userAge: UInt? = null,
    val userName: String = "",
    val userTextInput: String = "",
    val screenState: OnboardingScreenState = OnboardingScreenState.WELCOME,
    val curStep: OnboardingStep = OnboardingStep.ASK_NAME,
    val chatMessages: List<ChatMessage> = emptyList(),
    val brushingTimes: List<String> = listOf("9:00"),
) : BaseState(isLoading = isLoading, error = error)
