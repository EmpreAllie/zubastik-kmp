package com.features.onboard.domain

import com.features.onboard.presentation.model.state.OnboardingStep

sealed interface OnboardingAction {
    data object ShowNextZubMessage: OnboardingAction
    data class ShowUserReply(val step: OnboardingStep): OnboardingAction
    data object OpenBrushingDialog: OnboardingAction
    data class ShowFinalUserMessage(val count: Int): OnboardingAction
}