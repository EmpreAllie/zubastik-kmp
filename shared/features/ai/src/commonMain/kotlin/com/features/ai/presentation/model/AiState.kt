package com.features.ai.presentation.model

import com.features.ai.domain.model.AiChatMessage
import com.features.base.presentation.model.BaseState
import com.features.base.domain.model.error.Error

data class AiState(
    override val isLoading: Boolean = false,
    override val error: Error? = null,

    val aiChatMessageList: List<AiChatMessage> = emptyList(),
    val isAiThinking: Boolean = false,
    val userInputText: String = "",

) : BaseState(
    isLoading = isLoading,
    error = error
)
