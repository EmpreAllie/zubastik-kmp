package com.features.onboard.presentation

import androidx.lifecycle.viewModelScope
import com.core.data.utils.DateTimeManager
import com.features.base.presentation.model.BaseViewModel
import com.features.onboard.presentation.model.OnboardingEffects
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.onboard.presentation.model.OnboardingState
import com.features.onboard.presentation.model.chat.ChatMessage
import com.features.onboard.presentation.model.state.OnboardingScreenState
import com.features.onboard.presentation.model.state.OnboardingStep
import com.features.ui.Res
import com.features.ui.askBrushing
import com.features.ui.iBrushMyTeeth
import com.features.ui.myNameIs
import com.features.ui.niceToMeetYou
import com.features.ui.timesADay
import com.features.ui.whatsYourName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

class OnboardingViewModel(

) : BaseViewModel<OnboardingState, OnboardingEvents, OnboardingEffects>(OnboardingState()) {

    override fun onEvent(event: OnboardingEvents){
        when (event) {

            // Отображение двух первых сообщений - от Зубастика и от пользователя
            OnboardingEvents.OnStartChatClicked -> processClick()

            OnboardingEvents.OnAlreadyFamiliarClicked -> sendEffect(OnboardingEffects.NavigateToMain)

            OnboardingEvents.OnExitOnboardingClicked -> sendEffect(OnboardingEffects.NavigateToMain)

            OnboardingEvents.OnNextClicked -> processNext()

            OnboardingEvents.OnBackClicked -> processBack()

            is OnboardingEvents.OnTextInputChanged -> {
                updateState {
                    it.copy(
                        userTextInput = event.text
                    )
                }
            }

            OnboardingEvents.OnBrushingDialogDismiss -> {
                updateState {
                    it.copy(
                        isBrushingDialogVisible = false
                    )
                }
            }

            is OnboardingEvents.OnBrushingDialogCountChanged -> {
                updateState {
                    it.copy(
                        brushingTimes = generateNewBrushingTimesList(event.count)
                    )
                }
            }

            is OnboardingEvents.OnBrushingDialogConfirm -> processBrushingDialog(event.count, event.times)

        }
    }

    private fun processClick() {
        viewModelScope.launch {
            updateState {
                it.copy(
                    screenState = OnboardingScreenState.CHAT
                )
            }

            showNextMessage()

            delay(1000)

            showUserReply(step = OnboardingStep.ASK_NAME)
        }
    }

    private fun processNext() {
        when (state.value.curStep) {
            OnboardingStep.ASK_NAME -> {

                viewModelScope.launch {
                    val enteredName = state.value.userTextInput

                    updateState {
                        it.copy(
                            userName = enteredName,
                            curStep = OnboardingStep.ASK_AGE,
                            userTextInput = ""
                        )
                    }

                    delay(1000)

                    showNextMessage()

                    delay(1000)

                    showUserReply(step = OnboardingStep.ASK_AGE)
                }
            }

            OnboardingStep.ASK_AGE -> {
                viewModelScope.launch {
                    val enteredAge = state.value.userTextInput.toIntOrNull()

                    updateState {
                        it.copy(
                            userAge = enteredAge,
                            curStep = OnboardingStep.ASK_BRUSHING
                        )
                    }


                    delay(1000)

                    showNextMessage()
                }
            }

            OnboardingStep.ASK_BRUSHING -> {
                updateState {
                    it.copy(
                        isBrushingDialogVisible = true
                    )
                }
            }

            OnboardingStep.COMPLETE -> {
                updateState {
                    it.copy(
                        screenState = OnboardingScreenState.TEETH
                    )
                }
            }

        }
    }

    private fun processBack() {
        if (state.value.screenState == OnboardingScreenState.TEETH) {
            updateState {
                it.copy(
                    screenState = OnboardingScreenState.CHAT,
                    curStep = OnboardingStep.COMPLETE
                )
            }
            return
        }

        when (state.value.curStep) {
            OnboardingStep.ASK_NAME -> {
                updateState {
                    it.copy(
                        screenState = OnboardingScreenState.WELCOME,
                        userTextInput = ""
                    )
                }
            }

            OnboardingStep.ASK_AGE -> {
                updateState {
                    it.copy(
                        curStep = OnboardingStep.ASK_NAME,
                        chatMessages = state.value.chatMessages.dropLast(2),
                        userTextInput = ""
                    )
                }
            }

            OnboardingStep.ASK_BRUSHING -> {
                updateState {
                    it.copy(
                        curStep = OnboardingStep.ASK_AGE,
                        chatMessages = state.value.chatMessages.dropLast(1),
                        userTextInput = ""
                    )
                }
            }

            OnboardingStep.COMPLETE -> {
                updateState {
                    it.copy(
                        curStep = OnboardingStep.ASK_BRUSHING,
                        chatMessages = state.value.chatMessages.dropLast(1),
                        isBrushingDialogVisible = true
                    )
                }
            }
        }
    }

    private fun processBrushingDialog(count: Int, times: List<String>) {
        viewModelScope.launch {
            updateState {
                it.copy(
                    isBrushingDialogVisible = false
                )
            }

            delay(500)

            val finalUserMessage = ChatMessage.user(
                id = state.value.chatMessages.last().id + 1,
                textRes = Res.string.iBrushMyTeeth,
                formatArgs = listOf(count, Res.string.timesADay),
                timeStamp = DateTimeManager.getCurrentTimeStamp() //getCurrentTimeStamp()
            )

            updateState {
                it.copy(
                    chatMessages = state.value.chatMessages + finalUserMessage,
                    curStep = OnboardingStep.COMPLETE
                )
            }
        }

        //saveBrushingSettings(count, times)
    }

    private fun generateNewBrushingTimesList(count: Int): List<String> {
        return when (count) {
            1 -> listOf("9:00")
            2 -> listOf("9:00", "21:00")
            3 -> listOf("9:00", "14:00", "21:00")
            4 -> listOf("9:00", "12:00", "16:00", "21:00")
            5 -> listOf("9:00", "11:00", "14:00", "17:00", "21:00")
            else -> emptyList()
        }
    }

    private fun showUserReply(step: OnboardingStep) {
        val userMessage = ChatMessage.user(
            id = state.value.chatMessages.last().id + 1,
            textRes = Res.string.myNameIs,
            timeStamp = DateTimeManager.getCurrentTimeStamp(),
            step = step
        )

        updateState {
            it.copy(
                chatMessages = it.chatMessages + userMessage
            )
        }
    }


    private fun showNextMessage() {
        when (state.value.curStep) {
            OnboardingStep.ASK_NAME -> {

                val firstMessage = ChatMessage.zub(
                    id = 1,
                    textRes = Res.string.whatsYourName,
                    timeStamp = DateTimeManager.getCurrentTimeStamp()
                )

                updateState {
                    it.copy(
                        chatMessages = listOf(firstMessage)
                    )
                }
            }

            OnboardingStep.ASK_AGE -> {
                // Первое сообщение на шаге "Спросить возраст" - сообщение Зубастика "Найс ту мит ю. Сколько тебе лет?"
                val niceToMeetYouMessage = ChatMessage.zub(
                    id = state.value.chatMessages.last().id + 1,
                    textRes = Res.string.niceToMeetYou,
                    formatArgs = listOf(state.value.userName),
                    timeStamp = DateTimeManager.getCurrentTimeStamp()
                )

                updateState {
                    it.copy(
                        chatMessages = state.value.chatMessages + niceToMeetYouMessage
                    )
                }
            }

            OnboardingStep.ASK_BRUSHING -> {

                viewModelScope.launch{
                    val askBrushingMessage = ChatMessage.zub(
                        id = state.value.chatMessages.last().id + 1,
                        textRes = Res.string.askBrushing,
                        timeStamp = DateTimeManager.getCurrentTimeStamp()
                    )

                    updateState {
                        it.copy(
                            chatMessages = state.value.chatMessages + askBrushingMessage,
                        )
                    }

                    delay(1000)

                    updateState {
                        it.copy(
                            isBrushingDialogVisible = true
                        )
                    }
                }
            }

            else -> {}
        }
    }
}