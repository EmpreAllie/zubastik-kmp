package com.features.ai.presentation

import androidx.lifecycle.viewModelScope
import com.core.platform.getSystemLanguage
import com.features.ai.domain.AiRepository
import com.features.ai.domain.model.AiChatMessage
import com.features.ai.presentation.model.AiEffects
import com.features.ai.presentation.model.AiEvents
import com.features.ai.presentation.model.AiState
import com.features.base.presentation.model.BaseViewModel
import kotlinx.coroutines.launch
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error

class AiViewModel(
    private val repository: AiRepository
): BaseViewModel<AiState, AiEvents, AiEffects>(AiState()) {

    init {
        // Загрузка истории сообщений с сервера
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch {
            repository.getHistory().collect { result ->
                handleGetMessagesResult(result = result)
            }
        }
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
        val text = state.value.userInputText

        updateState { it.copy(userInputText = "") }

        viewModelScope.launch {
            repository.sendUserMessageToServer(
                userMessageText = text
            ).collect { result ->
                handleSendMessageResult(result)
            }
        }
    }

    private fun handleGetMessagesResult(
        result: Result<List<AiChatMessage>, Error>,
    ) {
        when(result) {
            Result.Loading -> updateState { it.copy(isLoading = true) }

            is Result.Success -> {
                updateState {
                    it.copy(
                        isLoading = false,
                        aiChatMessageList = result.data,
                    )
                }

                if (result.data.isEmpty()) greetUser()
            }

            is Result.Failure -> {
                updateState { it.copy(isLoading = false, error = result.error) }
            }

            Result.ConnectionError -> {
                updateState {
                    it.copy(
                        isLoading = false,
                        error = Error.CONNECTION
                    )
                }
            }

            else -> {}
        }
    }

    private fun greetUser() {
        viewModelScope.launch {
            repository.greetUser(getSystemLanguage()).collect { result ->
                handleGreetResult(result)
            }
        }
    }

    private fun handleGreetResult(result: Result<AiChatMessage, Error>) {
        when (result) {
            is Result.Success -> updateState {
                it.copy(aiChatMessageList = it.aiChatMessageList + result.data)
            }

            is Result.Failure -> {}

            Result.ConnectionError -> {}

            else -> {}
        }
    }

    private fun handleSendMessageResult(
        result: Result<List<AiChatMessage>, Error>,
    ) {
        when(result) {
            Result.Loading -> updateState { it.copy(isAiThinking = true) }

            is Result.Success -> {
                updateState {
                    it.copy(
                        isAiThinking = false,
                        aiChatMessageList = it.aiChatMessageList + result.data,
                    )
                }
            }

            is Result.Failure -> {
                updateState { it.copy(isAiThinking = false, error = result.error) }
            }

            Result.ConnectionError -> {
                updateState {
                    it.copy(
                        isAiThinking = false,
                        error = Error.CONNECTION
                    )
                }
            }

            else -> {}
        }
    }

}