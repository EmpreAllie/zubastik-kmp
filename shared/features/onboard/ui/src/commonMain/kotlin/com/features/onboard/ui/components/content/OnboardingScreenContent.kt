package com.features.onboard.ui.components.content

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.unit.dp
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.onboard.presentation.model.OnboardingState
import com.features.onboard.presentation.model.state.OnboardingScreenState
import com.features.onboard.presentation.model.state.OnboardingStep
import com.features.onboard.ui.components.content.inner.dialogs.OnboardingBrushingDialog
import com.features.onboard.ui.components.content.inner.screens.chat.OnboardingChatContent
import com.features.onboard.ui.components.content.inner.screens.OnboardingTeethContent
import com.features.onboard.ui.components.content.inner.screens.OnboardingWelcomeContent
import com.features.onboard.ui.components.content.inner.UpperStateRow
import com.features.ui.Res
import com.features.ui.alreadyFamiliar
import com.features.ui.back
import com.features.ui.begin
import com.features.ui.button.MainButton
import com.features.ui.finish
import com.features.ui.next
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingScreenContent(
    state: OnboardingState,
    onEvent: (OnboardingEvents) -> Unit
) {
    val lazyListState = rememberLazyListState()
    LaunchedEffect(state.chatMessages.size) {
        if (state.chatMessages.isNotEmpty()) {
            lazyListState.animateScrollToItem(index = 0/*state.chatMessages.last().id*/)
        }
    }

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .focusable()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 64.dp)
        ) {

            // Верхняя плашка
            UpperStateRow(state = state)

            Spacer(modifier = Modifier.height(state.screenState.upperSpaceHeight))

/*
            val boxBaseModifier = Modifier
                .fillMaxWidth()
                .shadow(
                    elevation = 1.dp,
                    shape = RoundedCornerShape(32.dp)
                )
                .clip(RoundedCornerShape(32.dp))
                .background(color = MainTheme.colors.containerBackground)
                .padding(vertical = 8.dp, horizontal = 24.dp)

            val finalBoxModifier = when (state.screenState) {
                OnboardingScreenState.WELCOME, OnboardingScreenState.CHAT -> {
                    boxBaseModifier.weight(1f)
                }

                else -> {
                    boxBaseModifier
                }
            }*/

            // Контейнер для чата/всего остального
            Box(
                modifier = Modifier
                    .onboardingContainer()
                    .then(
                        if (state.screenState == OnboardingScreenState.WELCOME || state.screenState == OnboardingScreenState.CHAT)
                                Modifier.weight(1f)
                        else
                                Modifier
                    )
            ) {
                when (state.screenState) {
                    OnboardingScreenState.WELCOME -> {
                        OnboardingWelcomeContent()
                    }

                    OnboardingScreenState.CHAT -> {
                        OnboardingChatContent(
                            state = state,
                            onEvent = onEvent,
                            lazyListState = lazyListState
                        )
                    }

                    OnboardingScreenState.TEETH -> {
                        OnboardingTeethContent(onEvent = onEvent)
                    }
                }
            }

            Spacer(modifier = Modifier.height(48.dp))

            // Кнопки навигации
            when (state.screenState) {
                OnboardingScreenState.WELCOME -> {
                    Column(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        verticalArrangement = Arrangement.spacedBy(12.dp),
                    ) {
                        // "Начать"
                        MainButton(
                            modifier = Modifier
                                .fillMaxWidth(),
                            text = stringResource(Res.string.begin),
                            textStyle = MainTheme.typography.onboarding.buttonText,
                            shape = RoundedCornerShape(10.dp),
                            contentColor = MainTheme.colors.secondary,
                            backgroundColor = MainTheme.colors.containerBackground,
                            onClick = {
                                onEvent(OnboardingEvents.OnStartChatClicked)
                            }
                        )
                        // "Мы уже знакомы"
                        MainButton(
                            modifier = Modifier
                                .fillMaxWidth()
                                .padding(horizontal = 24.dp),
                            text = stringResource(Res.string.alreadyFamiliar),
                            textStyle = MainTheme.typography.onboarding.alreadyFamiliar,
                            shape = RoundedCornerShape(10.dp),
                            contentColor = MainTheme.colors.containerBackground,
                            backgroundColor = MainTheme.colors.secondary,
                            onClick = {
                                onEvent(OnboardingEvents.OnAlreadyFamiliarClicked)
                            }
                        )
                    }
                }

                OnboardingScreenState.CHAT -> {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 32.dp),
                        horizontalArrangement = Arrangement.spacedBy(12.dp)
                    ) {
                        // "Назад"
                        MainButton(
                            modifier = Modifier.weight(1f),
                            text = stringResource(Res.string.back),
                            textStyle = MainTheme.typography.onboarding.buttonText,
                            shape = RoundedCornerShape(10.dp),
                            contentColor = MainTheme.colors.secondary,
                            backgroundColor = MainTheme.colors.containerBackground,
                            onClick = {
                                onEvent(OnboardingEvents.OnBackClicked)
                            }
                        )

                        // "Далее"
                        MainButton(
                            modifier = Modifier.weight(1f),
                            text = if (state.curStep == OnboardingStep.COMPLETE)
                                stringResource(Res.string.finish)
                            else
                                stringResource(Res.string.next),
                            textStyle = MainTheme.typography.onboarding.buttonText,
                            shape = RoundedCornerShape(10.dp),
                            contentColor = MainTheme.colors.containerBackground,
                            backgroundColor = MainTheme.colors.secondary,
                            isEnabled = state.userTextInput.isNotEmpty(),
                            onClick = {
                                onEvent(OnboardingEvents.OnNextClicked)
                            }
                        )
                    }
                }

                OnboardingScreenState.TEETH -> {

                }
            }
        }


        // Диалоговое окно с частотой чистки зубов
        if (state.isBrushingDialogVisible) {
            OnboardingBrushingDialog(
                state = state,
                onEvent = onEvent,
                onConfirm = {
                    onEvent(OnboardingEvents.OnBrushingDialogConfirm(
                        count = state.brushingTimes.size,
                        times = state.brushingTimes
                    ))
                },

                onDismiss = {
                    onEvent(OnboardingEvents.OnBrushingDialogDismiss)
                }
            )
        }
    }
}


private val OnboardingScreenState.upperSpaceHeight
    get() = when (this) {
        OnboardingScreenState.WELCOME -> 24.dp
        OnboardingScreenState.CHAT -> 24.dp
        OnboardingScreenState.TEETH -> 8.dp
    }

@Composable
private fun Modifier.onboardingContainer(): Modifier = this
        .fillMaxWidth()
        .shadow(elevation = 1.dp, shape = RoundedCornerShape(32.dp))
        .clip(RoundedCornerShape(32.dp))
        .background(MainTheme.colors.containerBackground)
        .padding(vertical = 8.dp, horizontal = 24.dp)
