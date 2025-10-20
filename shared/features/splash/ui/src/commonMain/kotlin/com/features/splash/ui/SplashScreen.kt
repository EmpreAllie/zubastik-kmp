package com.features.splash.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil3.compose.LocalPlatformContext
import com.core.data.utils.localize
import com.features.splash.presentation.model.SplashEvents
import com.features.splash.presentation.SplashViewModel
import com.features.splash.presentation.model.SplashEffects
import com.features.splash.ui.components.SplashScreenContent
import com.features.ui.dialog.DialogError
import com.features.ui.extension.CloseApp
import com.resources.MultiplatformResource
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvents
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    rootViewModel: RootViewModel = koinViewModel(),
) {
    val context = LocalPlatformContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.onEvent(SplashEvents.Initialize)

        viewModel.effect.collect { effect ->
            when (effect) {
                is SplashEffects.NavigateToScreen -> rootViewModel.onEvent(
                    RootEvents.OnSetScreen(
                        screen = effect.screen,
                        isClearStack = true
                    )
                )
            }
        }
    }

    SplashScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )

    state.errorText?.let { errorText ->
        DialogError(
            title = MultiplatformResource.strings.appName.localize(),
            description = errorText
        ) {
            viewModel.onEvent(SplashEvents.OnCloseDialog)
            CloseApp(context)
        }
    }
}