package com.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.features.auth.presentation.AuthCodeViewModel
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.ui.components.content.AuthCodeScreenContent
import com.features.base.domain.enum.Screen
import com.features.ui.LoadingContent
import com.features.ui.Res
import com.features.ui.code
import com.features.ui.dialog.DialogError
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthCodeScreen(
    viewModel: AuthCodeViewModel = koinViewModel(),
    rootViewModel: RootViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel, lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {

            viewModel.onEvent(AuthEvents.OnStartResendCodeTimer)

            viewModel.effect.collect { effect ->
                when (effect) {

                    AuthEffects.NavigateBack -> rootViewModel.onEvent(RootEvent.OnClickBack)

                    AuthEffects.NavigateToOnboarding -> {
                        rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.ONBOARDING, isClearStack = true))
                    }

                    AuthEffects.NavigateToMain -> {
                        rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.MAIN, isClearStack = true))
                    }

                    else -> {}

                }
            }
        }
    }

    AuthCodeScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )

    if (state.isLoading) {
        LoadingContent()
    }


    state.error?.let { error ->
        DialogError(
            title = stringResource(Res.string.code),
            error = error,
            onClose = {
                viewModel.onEvent(AuthEvents.OnCloseErrorDialog)
            }
        )
    }
}