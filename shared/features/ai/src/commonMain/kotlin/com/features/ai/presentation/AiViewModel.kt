package com.features.ai.presentation

import com.features.ai.domain.AiRepository
import com.features.ai.presentation.model.AiEffects
import com.features.ai.presentation.model.AiEvents
import com.features.ai.presentation.model.AiState
import com.features.base.presentation.model.BaseViewModel

class AiViewModel(
    private val repository: AiRepository
): BaseViewModel<AiState, AiEvents, AiEffects>(AiState()) {

    init {
        // Загрузка истории сообщений с сервера
    }

    override fun onEvent(event: AiEvents) {
        when(event) {
            is AiEvents.OnUserTextInputChanged -> {
                updateState { it.copy(userInputText = event.newText) }
            }

            is AiEvents.OnUserMessageSentToServer -> processUserMessageSentToServer()

            AiEvents.OnCloseErrorDialog -> updateState { it.copy(error = null) }

        }
    }

    private fun processUserMessageSentToServer() {
        // repository.send()...
    }

}