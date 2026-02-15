package com.features.onboard.presentation.model

interface OnboardingEvents {
    data object OnStartChatClicked : OnboardingEvents
    data object OnAlreadyFamiliarClicked: OnboardingEvents

    data object OnBackClicked: OnboardingEvents
    data object OnNextClicked: OnboardingEvents

    data class OnTextInputChanged(val text: String) : OnboardingEvents

    data object OnBrushingDialogDismiss: OnboardingEvents

}