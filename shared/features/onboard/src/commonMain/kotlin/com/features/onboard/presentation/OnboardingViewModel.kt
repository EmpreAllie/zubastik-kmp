package com.features.onboard.presentation

import androidx.lifecycle.viewModelScope
import com.core.data.utils.DateTimeManager
import com.features.base.presentation.model.BaseViewModel
import com.features.onboard.domain.OnboardingAction
import com.features.onboard.domain.OnboardingRepository
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
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.IO
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext

class OnboardingViewModel(
    private val dateTimeManager: DateTimeManager,
    private val repository: OnboardingRepository,
) : BaseViewModel<OnboardingState, OnboardingEvents, OnboardingEffects>(OnboardingState()) {

    override fun onEvent(event: OnboardingEvents){
        when (event) {

            // Отображение двух первых сообщений - от Зубастика и от пользователя
            OnboardingEvents.OnStartChatClicked -> processStartChat()

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

    private fun processBrushingDialog(count: Int, times: List<String>) {
        updateState { it.copy(isBrushingDialogVisible = false) }
        executeSequence(OnboardingStep.COMPLETE, count = count)
        //saveBrushingSettings(count, times)
    }


    private fun processStartChat() {
        updateState { it.copy(screenState = OnboardingScreenState.CHAT) }
        executeSequence(OnboardingStep.ASK_NAME)
    }

    private fun processNext() {
        when (state.value.curStep) {
            OnboardingStep.ASK_NAME -> processNameStep()
            OnboardingStep.ASK_AGE -> processAgeStep()
            OnboardingStep.ASK_BRUSHING -> updateState { it.copy(isBrushingDialogVisible = true) }
            OnboardingStep.COMPLETE -> updateState { it.copy(screenState = OnboardingScreenState.TEETH) }
        }
    }

    private fun processNameStep() {
        val enteredName = state.value.userTextInput
        updateState {
            it.copy(
                userName = enteredName,
                curStep = OnboardingStep.ASK_AGE,
                userTextInput = ""
            )
        }

        executeSequence(OnboardingStep.ASK_AGE)
    }

    private fun processAgeStep() {
        val enteredAge = state.value.userTextInput.toUIntOrNull()
        updateState {
            it.copy(
                userAge = enteredAge,
                curStep = OnboardingStep.ASK_BRUSHING
            )
        }
        executeSequence(OnboardingStep.ASK_BRUSHING)
    }

    private fun executeSequence(step: OnboardingStep, count: Int = 0) {
        viewModelScope.launch {
            repository.sequence(step).collect { action ->
                when(action) {
                    OnboardingAction.ShowNextZubMessage -> showNextZubMessage()
                    is OnboardingAction.ShowUserReply -> showNextUserMessage(action.step)
                    OnboardingAction.OpenBrushingDialog -> {
                        updateState{ it.copy(isBrushingDialogVisible = true) }
                    }
                    is OnboardingAction.ShowFinalUserMessage -> {
                        renderFinalMessage(count)
                    }
                }
            }
        }
    }

    private fun showNextZubMessage() {
        val lastId = state.value.chatMessages.lastOrNull()?.id ?: 0
        val nextMessage = when(state.value.curStep) {
            OnboardingStep.ASK_NAME -> ChatMessage.zub(
                id = 1,
                textRes = Res.string.whatsYourName,
                timeStamp = dateTimeManager.getCurrentTimeStamp()
            )

            OnboardingStep.ASK_AGE -> ChatMessage.zub(
                id = lastId + 1,
                textRes = Res.string.niceToMeetYou,
                formatArgs = listOf(state.value.userName),
                timeStamp = dateTimeManager.getCurrentTimeStamp()
            )

            OnboardingStep.ASK_BRUSHING -> ChatMessage.zub(
                id = lastId + 1,
                textRes = Res.string.askBrushing,
                timeStamp = dateTimeManager.getCurrentTimeStamp()
            )

            else -> null
        }

        nextMessage?.let { msg ->
            updateState {
                it.copy(
                    chatMessages = if (state.value.curStep == OnboardingStep.ASK_NAME) listOf(msg) else it.chatMessages + msg
                )
            }
        }
    }

    private fun showNextUserMessage(step: OnboardingStep) {
        val lastId = state.value.chatMessages.lastOrNull()?.id ?: 0
        val userMessage = ChatMessage.user(
            id = lastId + 1,
            textRes = Res.string.myNameIs,
            timeStamp = dateTimeManager.getCurrentTimeStamp(),
            step = step
        )

        updateState {
            it.copy(
                chatMessages = it.chatMessages + userMessage
            )
        }
    }

    private fun renderFinalMessage(count: Int) {
        val lastId = state.value.chatMessages.lastOrNull()?.id ?: 0
        val finalUserMessage = ChatMessage.user(
            id = lastId + 1,
            textRes = Res.string.iBrushMyTeeth,
            formatArgs = listOf(count, Res.string.timesADay),
            timeStamp = dateTimeManager.getCurrentTimeStamp()
        )

        updateState {
            it.copy(
                chatMessages = state.value.chatMessages + finalUserMessage,
                curStep = OnboardingStep.COMPLETE
            )
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
}