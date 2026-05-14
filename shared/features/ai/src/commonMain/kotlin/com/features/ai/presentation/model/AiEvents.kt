package com.features.ai.presentation.model

sealed interface AiEvents {
    data class OnUserTextInputChanged(val newText: String): AiEvents
    data class OnUserMessageSentToServer(val userText: String): AiEvents
    data object OnCloseErrorDialog: AiEvents
}