package com.features.splash.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.features.splash.presentation.SplashViewModel
import com.features.splash.presentation.model.SplashEffects
import com.features.splash.presentation.model.SplashEvents
import com.features.splash.ui.components.SplashScreenContent
import com.features.ui.Res
import com.features.ui.appName
import com.features.ui.dialog.DialogError
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    rootViewModel: RootViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.clearEffects()
        viewModel.effect.collect { effect ->
            when (effect) {
                is SplashEffects.NavigateToScreen -> {
                    rootViewModel.onEvent(
                        RootEvent.OnSetScreen(destination = effect.screen, isClearStack = true),
                    )
                }
            }
        }
    }

    SplashScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
    )

    state.error?.let { error ->
        DialogError(
            title = stringResource(Res.string.appName),
            error = error,
            onClose = {
                viewModel.onEvent(SplashEvents.OnCloseDialog)
            },
        )
    }
}
