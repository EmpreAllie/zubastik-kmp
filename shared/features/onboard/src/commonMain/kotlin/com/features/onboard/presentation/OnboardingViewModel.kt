package com.features.onboard.presentation

import androidx.lifecycle.viewModelScope
import com.core.data.utils.DateTimeManager
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseViewModel
import com.features.onboard.domain.OnboardingAction
import com.features.onboard.domain.OnboardingRepository
import com.features.onboard.presentation.model.OnboardingEffects
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.onboard.presentation.model.OnboardingState
import com.features.onboard.presentation.model.chat.OnboardingChatMessage
import com.features.onboard.presentation.model.state.OnboardingScreenState
import com.features.onboard.presentation.model.state.OnboardingStep
import com.features.teeth.domain.TeethRepository
import com.features.ui.Res
import com.features.ui.askBrushing
import com.features.ui.iBrushMyTeeth
import com.features.ui.myNameIs
import com.features.ui.niceToMeetYou
import com.features.ui.timesADay
import com.features.ui.whatsYourName
import kotlinx.coroutines.launch

class OnboardingViewModel(
    private val dateTimeManager: DateTimeManager,
    private val repository: OnboardingRepository,
    private val teethRepository: TeethRepository,
) : BaseViewModel<OnboardingState, OnboardingEvents, OnboardingEffects>(OnboardingState()) {

    override fun onEvent(event: OnboardingEvents){
        when (event) {

            // Отображение двух первых сообщений - от Зубастика и от пользователя
            OnboardingEvents.OnStartChatClicked -> processStartChat()

            OnboardingEvents.OnAlreadyFamiliarClicked -> sendEffect(OnboardingEffects.NavigateToMain)

            OnboardingEvents.OnExitOnboardingClicked -> processExitOnboarding()

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

            OnboardingEvents.OnCloseErrorDialog -> updateState { it.copy(error = null) }

        }
    }


    private fun processExitOnboarding() {
        viewModelScope.launch {
            var hasError = false

            repository.sendProfileInfoToServer(
                name = state.value.userName,
                age = state.value.userAge?.toInt() ?: 0,
            ).collect { result ->
                handleResult(result) {hasError = true}
            }
            if (hasError) return@launch

            repository.sendBrushingScheduleToServer(
                timesBrushing = state.value.brushingTimes
            ).collect { result ->
                handleResult(result) {hasError = true}
            }
            if (hasError) return@launch

            repository.sendTeethInfoToServer(
                teeth = teethRepository.teeth.value
            ).collect { result ->
                handleResult(result) {hasError = true}
            }
            if (hasError) return@launch

            sendEffect(OnboardingEffects.NavigateToMain)
        }
    }

    private fun handleResult(
        result: Result<Unit, Error>,
        onError: () -> Unit
    ) {

        when(result) {
            Result.Loading -> updateState { it.copy(isLoading = true) }
            is Result.Success -> updateState { it.copy(isLoading = false) }

            is Result.Failure -> {
                updateState { it.copy(isLoading = false, error = result.error) }
                onError()
            }

            Result.ConnectionError -> {
                updateState {
                    it.copy(
                        isLoading = false,
                        error = Error.CONNECTION
                    )
                }
                onError()
            }

            else -> {}
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
        val lastId = state.value.onboardingChatMessages.lastOrNull()?.id ?: 0
        val nextMessage = when(state.value.curStep) {
            OnboardingStep.ASK_NAME -> OnboardingChatMessage.zub(
                id = 1,
                textRes = Res.string.whatsYourName,
                timeStamp = dateTimeManager.getCurrentTimeStamp()
            )

            OnboardingStep.ASK_AGE -> OnboardingChatMessage.zub(
                id = lastId + 1,
                textRes = Res.string.niceToMeetYou,
                formatArgs = listOf(state.value.userName),
                timeStamp = dateTimeManager.getCurrentTimeStamp()
            )

            OnboardingStep.ASK_BRUSHING -> OnboardingChatMessage.zub(
                id = lastId + 1,
                textRes = Res.string.askBrushing,
                timeStamp = dateTimeManager.getCurrentTimeStamp()
            )

            else -> null
        }

        nextMessage?.let { msg ->
            updateState {
                it.copy(
                    onboardingChatMessages = if (state.value.curStep == OnboardingStep.ASK_NAME)
                        listOf(msg)
                    else
                        it.onboardingChatMessages + msg
                )
            }
        }
    }

    private fun showNextUserMessage(step: OnboardingStep) {
        val lastId = state.value.onboardingChatMessages.lastOrNull()?.id ?: 0
        val userMessage = OnboardingChatMessage.user(
            id = lastId + 1,
            textRes = Res.string.myNameIs,
            timeStamp = dateTimeManager.getCurrentTimeStamp(),
            step = step
        )

        updateState {
            it.copy(
                onboardingChatMessages = it.onboardingChatMessages + userMessage
            )
        }
    }

    private fun renderFinalMessage(count: Int) {
        val lastId = state.value.onboardingChatMessages.lastOrNull()?.id ?: 0
        val finalUserMessage = OnboardingChatMessage.user(
            id = lastId + 1,
            textRes = Res.string.iBrushMyTeeth,
            formatArgs = listOf(count, Res.string.timesADay),
            timeStamp = dateTimeManager.getCurrentTimeStamp()
        )

        updateState {
            it.copy(
                onboardingChatMessages = state.value.onboardingChatMessages + finalUserMessage,
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
                        onboardingChatMessages = state.value.onboardingChatMessages.dropLast(2),
                        userTextInput = ""
                    )
                }
            }

            OnboardingStep.ASK_BRUSHING -> {
                updateState {
                    it.copy(
                        curStep = OnboardingStep.ASK_AGE,
                        onboardingChatMessages = state.value.onboardingChatMessages.dropLast(1),
                        userTextInput = ""
                    )
                }
            }

            OnboardingStep.COMPLETE -> {
                updateState {
                    it.copy(
                        curStep = OnboardingStep.ASK_BRUSHING,
                        onboardingChatMessages = state.value.onboardingChatMessages.dropLast(1),
                        isBrushingDialogVisible = true
                    )
                }
            }
        }
    }
}