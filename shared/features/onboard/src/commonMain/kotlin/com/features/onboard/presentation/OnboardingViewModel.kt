package com.features.onboard.presentation

import androidx.lifecycle.viewModelScope
import com.features.base.presentation.model.BaseViewModel
import com.features.onboard.presentation.model.OnboardingEffects
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.onboard.presentation.model.OnboardingState
import com.features.onboard.presentation.model.utils.Author
import com.features.onboard.presentation.model.utils.ChatMessage
import com.features.onboard.presentation.model.utils.OnboardingScreenState
import com.features.onboard.presentation.model.utils.OnboardingStep
import com.features.ui.Res
import com.features.ui.myNameIs
import com.features.ui.whatsYourName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnboardingViewModel()
    : BaseViewModel<OnboardingState, OnboardingEvents, OnboardingEffects>(OnboardingState()) {

    override fun onEvent(event: OnboardingEvents){
        when (event) {

            // Отображение двух первых сообщений - от Зубастика и от пользователя
            OnboardingEvents.OnStartChatClicked -> {

                viewModelScope.launch {
                    updateState {
                        it.copy(
                            screenState = OnboardingScreenState.CHAT
                        )
                    }

                    showNextMessage()

                    delay(1000)

                    showFirstUserReply()
                }

            }

            OnboardingEvents.OnAlreadyFamiliarClicked -> {
                sendEffect(OnboardingEffects.NavigateToMain)
            }

        }
    }

    private fun showFirstUserReply() {
        when (state.value.curStep) {
            OnboardingStep.ASK_NAME -> {
                val userMessage = ChatMessage(
                    id = state.value.chatMessages.last().id + 1,
                    textRes = Res.string.myNameIs,
                    author = Author.USER,
                    timeStamp = "00:00"
                )

                updateState{
                    it.copy(
                        chatMessages = it.chatMessages + userMessage
                    )
                }
            }

            else -> {}
        }
    }


    private fun showNextMessage() {
        when (state.value.curStep) {
            OnboardingStep.ASK_NAME -> {

                val firstMessage = ChatMessage(
                    id = 1,
                    textRes = Res.string.whatsYourName,
                    author = Author.ZUB,
                    timeStamp = "00:00"
                )

                updateState {
                    it.copy(
                        chatMessages = listOf(firstMessage)
                    )
                }
            }

            else -> {}
        }
    }
}