package com.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil3.compose.LocalPlatformContext
import com.features.auth.presentation.AuthViewModel
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.ui.components.AuthCodeScreenContent
import com.features.ui.dialog.DialogError
import com.features.ui.extension.CloseApp
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthCodeScreen(
    viewModel: AuthViewModel,// = koinViewModel(),
    onNavigateBack: () -> Unit
) {
    val context = LocalPlatformContext.current
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is AuthEffects.NavigateToPhoneInput -> {
                    onNavigateBack()
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onCodeScreenEntered()
    }

    AuthCodeScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )


    state.error?.let { error ->
        DialogError(
            title = "Code Screen",
            error = error,
            onClose = {
                viewModel.onEvent(AuthEvents.OnCloseDialog)
                CloseApp(context)
            }
        )
    }
}