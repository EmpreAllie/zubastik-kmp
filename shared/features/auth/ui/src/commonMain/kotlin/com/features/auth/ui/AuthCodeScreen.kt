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
import com.features.base.domain.enum.Graph
import com.features.base.domain.enum.Screen
import com.features.ui.dialog.DialogError
import com.features.ui.extension.CloseApp
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun AuthCodeScreen(
    //viewModel: AuthViewModel = koinViewModel(),
    //onNavigateBack: () -> Unit
) {
    val viewModel: AuthViewModel = koinViewModel(qualifier = named(Graph.AUTH.route))
    val state by viewModel.state.collectAsState()
    val rootViewModel: RootViewModel = koinViewModel() // Получаем RootViewModel
    /*
LaunchedEffect(Unit) {
    viewModel.effect.collect { effect ->
        when(effect) {
            is AuthEffects.NavigateToPhoneInput -> {
                onNavigateBack()
            }
        }
    }
}*/


    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                // Ловим сайд-эффект от AuthViewModel...
                AuthEffects.NavigateToMain -> {
                    // ... и транслируем его в событие для RootViewModel
                    rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.MAIN, isClearStack = true))
                }

                AuthEffects.NavigateToPhoneInput -> {
                    rootViewModel.onEvent(RootEvent.OnClickBack)
                }
            }
        }
    }

    LaunchedEffect(Unit) {
        viewModel.onEvent(AuthEvents.OnStartResendCodeTimer)
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
            }
        )
    }
}