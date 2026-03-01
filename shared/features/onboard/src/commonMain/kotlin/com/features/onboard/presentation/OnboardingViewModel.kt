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
import com.features.ui.askBrushing
import com.features.ui.iBrushMyTeeth
import com.features.ui.myNameIs
import com.features.ui.niceToMeetYou
import com.features.ui.timesADay
import com.features.ui.whatsYourName
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import org.jetbrains.compose.resources.stringResource
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class OnboardingViewModel(

) : BaseViewModel<OnboardingState, OnboardingEvents, OnboardingEffects>(OnboardingState()) {

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

            OnboardingEvents.OnExitOnboardingClicked -> {
                sendEffect(OnboardingEffects.NavigateToMain)
            }

            OnboardingEvents.OnNextClicked -> {
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

                            showSecondUserReply()
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

            OnboardingEvents.OnBackClicked -> {
                println("ZUB_DEBUG: Back clicked. Current messages size: ${state.value.chatMessages.size}")
                if (state.value.screenState == OnboardingScreenState.TEETH) {
                    updateState {
                        it.copy(
                            screenState = OnboardingScreenState.CHAT,
                            curStep = OnboardingStep.COMPLETE
                        )
                    }
                    return@onEvent
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

            is OnboardingEvents.OnBrushingDialogConfirm -> {
                viewModelScope.launch {
                    updateState {
                        it.copy(
                            isBrushingDialogVisible = false
                        )
                    }

                    delay(500)

                    val finalUserMessage = ChatMessage(
                        id = state.value.chatMessages.last().id + 1,
                        textRes = Res.string.iBrushMyTeeth,
                        formatArgs = listOf(event.count, Res.string.timesADay),
                        author = Author.USER,
                        timeStamp = getCurrentTimeStamp()
                    )

                    updateState {
                        it.copy(
                            chatMessages = state.value.chatMessages + finalUserMessage,
                            curStep = OnboardingStep.COMPLETE
                        )
                    }
                }

                //saveBrushingSettings(event.count, event.times)
            }

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

    @OptIn(ExperimentalTime::class)
    private fun getCurrentTimeStamp(): String {
        val now = Clock.System.now()
        val localTime = now.toLocalDateTime(TimeZone.currentSystemDefault())

        val hours = localTime.hour.toString().padStart(2, '0')
        val minutes = localTime.minute.toString().padStart(2, '0')

        return "$hours:$minutes"
    }


    private fun showFirstUserReply() {
        when (state.value.curStep) {
            OnboardingStep.ASK_NAME -> {
                val userMessage = ChatMessage(
                    id = state.value.chatMessages.last().id + 1,
                    textRes = Res.string.myNameIs,
                    author = Author.USER,
                    timeStamp = getCurrentTimeStamp(),
                    createdOnStep = OnboardingStep.ASK_NAME
                )

                updateState {
                    it.copy(
                        chatMessages = it.chatMessages + userMessage
                    )
                }
            }

            else -> {}
        }
    }

    private fun showSecondUserReply() {

        val userMessage = ChatMessage(
            id = state.value.chatMessages.last().id + 1,
            textRes = Res.string.myNameIs,
            author = Author.USER,
            timeStamp = getCurrentTimeStamp(),
            createdOnStep = OnboardingStep.ASK_AGE
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

                val firstMessage = ChatMessage(
                    id = 1,
                    textRes = Res.string.whatsYourName,
                    author = Author.ZUB,
                    timeStamp = getCurrentTimeStamp()
                )

                updateState {
                    it.copy(
                        chatMessages = listOf(firstMessage)
                    )
                }
            }

            OnboardingStep.ASK_AGE -> {
                // Первое сообщение на шаге "Спросить возраст" - сообщение Зубастика "Найс ту мит ю. Сколько тебе лет?"
                val niceToMeetYouMessage = ChatMessage(
                    id = state.value.chatMessages.last().id + 1,
                    textRes = Res.string.niceToMeetYou,
                    formatArgs = listOf(state.value.userName),
                    author = Author.ZUB,
                    timeStamp = getCurrentTimeStamp()
                )

                updateState {
                    it.copy(
                        chatMessages = state.value.chatMessages + niceToMeetYouMessage
                    )
                }
            }

            OnboardingStep.ASK_BRUSHING -> {

                viewModelScope.launch{
                    val askBrushingMessage = ChatMessage(
                        id = state.value.chatMessages.last().id + 1,
                        textRes = Res.string.askBrushing,
                        author = Author.ZUB,
                        timeStamp = getCurrentTimeStamp()
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