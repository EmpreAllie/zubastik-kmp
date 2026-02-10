package com.features.onboard.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.features.base.domain.enum.Screen
import com.features.onboard.presentation.OnboardingViewModel
import com.features.onboard.presentation.model.OnboardingEffects
import com.features.onboard.ui.components.content.OnboardingScreenContent
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = koinViewModel(),
    rootViewModel: RootViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

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
        onEvent = viewModel::onEvent
    )
}