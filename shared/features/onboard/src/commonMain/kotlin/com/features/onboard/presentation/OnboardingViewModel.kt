package com.features.onboard.presentation

import com.features.base.presentation.model.BaseViewModel
import com.features.onboard.presentation.model.Author
import com.features.onboard.presentation.model.ChatMessage
import com.features.onboard.presentation.model.OnboardingEffects
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.onboard.presentation.model.OnboardingScreenState
import com.features.onboard.presentation.model.OnboardingState
import com.features.onboard.presentation.model.OnboardingStep
import com.features.ui.Res
import com.features.ui.whatsYourName

class OnboardingViewModel()
    : BaseViewModel<OnboardingState, OnboardingEvents, OnboardingEffects>(OnboardingState()) {


    override fun onEvent(event: OnboardingEvents){
        when (event) {
            OnboardingEvents.OnStartChatClicked -> {
                updateState {
                    it.copy(
                        screenState = OnboardingScreenState.CHAT
                    )
                }
                showNextMessage()
            }
        }
    }


    private fun showNextMessage() {
        when (state.value.curStep) {
            OnboardingStep.ASK_NAME -> {

                val firstMessage = ChatMessage(
                    id = 1,
                    text = Res.string.whatsYourName,
                    author = Author.ZUB
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