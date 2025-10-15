package com.features.splash.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil3.compose.LocalPlatformContext
import com.core.data.utils.localize
import com.features.splash.presentation.model.SplashEvents
import com.features.splash.presentation.SplashViewModel
import com.features.splash.presentation.model.SplashNavigationEvent
import com.features.splash.ui.components.SplashScreenContent
import com.features.ui.dialog.DialogError
import com.features.ui.extension.CloseApp
import com.resources.MultiplatformResource
import com.root.presentation.RootViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun SplashScreen(
    viewModel: SplashViewModel = koinViewModel(),
    rootViewModel: RootViewModel = koinViewModel(),
) {
    val context = LocalPlatformContext.current
    val state by viewModel.state.collectAsState()

    // для запуска в первый раз, т.к. Unit никогда не изменится,
    // и LaunchedEffect вызовет функцию loadAndNavigate() только 1 раз
    LaunchedEffect(Unit) {
        viewModel.loadAndNavigate()
    }

    // для запуска и ожидания остальных событий
    LaunchedEffect(viewModel.navigationEvent) {
        viewModel.navigationEvent.collect { event ->
            when(event) {
                SplashNavigationEvent.NavigateToAuth -> {
                    //rootViewModel.navigateTo()
                }
                SplashNavigationEvent.NavigateToMain -> {
                    //rootViewModel.navigateTo()
                }
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