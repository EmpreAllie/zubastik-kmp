package com.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil3.compose.LocalPlatformContext
import com.features.auth.presentation.AuthViewModel
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.ui.components.AuthPhoneScreenContent
import com.features.base.domain.enum.Graph
import com.features.base.domain.enum.Screen
import com.features.ui.Res
import com.features.ui.authorization
import com.features.ui.dialog.DialogError
import com.features.ui.extension.CloseApp
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import kotlinx.coroutines.flow.collect
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.qualifier.named

@Composable
fun AuthPhoneScreen(
    //viewModel: AuthViewModel = koinViewModel(),
    //onNavigateToCodeInput: () -> Unit
) {
    //val state by viewModel.state.collectAsState()

    /*
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is AuthEffects.NavigateToCodeInput -> {
                    onNavigateToCodeInput()
                }
            }
        }
    }*/

    val viewModel: AuthViewModel = koinViewModel(qualifier = named(Graph.AUTH.route))
    val rootViewModel: RootViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                // Когда AuthViewModel говорит "перейти к вводу кода"...
                is AuthEffects.NavigateToCodeInput -> {
                    // ...мы говорим RootViewModel "установи экран ввода кода".
                    rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.CONFIRM))
                }
            }
        }
    }


    AuthPhoneScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )


    // обработчик ошибок
    state.error?.let {  error ->
        DialogError(
            title = stringResource(Res.string.authorization),
            error = error,
            onClose = {
                viewModel.onEvent(AuthEvents.OnCloseDialog)
            }
        )
    }
}