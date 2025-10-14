package com.features.splash.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil3.compose.LocalPlatformContext
import com.core.data.utils.localize
import com.features.splash.presentation.model.SplashEvents
import com.features.splash.presentation.SplashViewModel
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

    SplashScreenContent(
        viewModel = viewModel,
        navigateToAuth = {

        },
        navigateToMain = {

        }
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