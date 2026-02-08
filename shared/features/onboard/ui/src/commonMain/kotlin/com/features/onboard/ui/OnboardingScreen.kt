package com.features.onboard.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.features.onboard.presentation.OnboardingViewModel
import com.features.onboard.ui.components.content.OnboardingScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingScreen(
    viewModel: OnboardingViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    OnboardingScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )
}