package com.features.onboard.ui

import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.features.base.domain.enum.Screen
import com.features.onboard.presentation.OnboardingViewModel
import com.features.onboard.presentation.model.OnboardingEffects
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.onboard.presentation.model.state.OnboardingScreenState
import com.features.onboard.ui.components.content.OnboardingScreenContent
import com.features.ui.LoadingContent
import com.features.ui.Res
import com.features.ui.dialog.DialogError
import com.features.ui.extension.BackHandler
import com.features.ui.onboarding
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = koinViewModel(),
    rootViewModel: RootViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    val lazyListState = rememberLazyListState()
    LaunchedEffect(state.onboardingChatMessages.size) {
        if (state.onboardingChatMessages.isNotEmpty()) {
            lazyListState.animateScrollToItem(index = 0)
        }
    }

    val isNotFirstScreen = state.screenState != OnboardingScreenState.WELCOME
    BackHandler(enabled = isNotFirstScreen) {
        viewModel.onEvent(OnboardingEvents.OnBackClicked)
    }


    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                OnboardingEffects.NavigateToMain -> {
                    rootViewModel.onEvent(RootEvent.OnSetScreen(
                        destination = Screen.MAIN,
                        isClearStack = true
                    ))
                }
            }
        }
    }

    OnboardingScreenContent(
        state = state,
        lazyListState = lazyListState,
        onEvent = viewModel::onEvent
    )

    if (state.isLoading) {
        LoadingContent()
    }

    state.error?.let {  error ->
        DialogError(
            title = stringResource(Res.string.onboarding),
            error = error,
            onClose = {
                viewModel.onEvent(OnboardingEvents.OnCloseErrorDialog)
            }
        )
    }
}