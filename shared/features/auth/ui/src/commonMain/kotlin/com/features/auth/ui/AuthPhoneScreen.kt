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
import com.features.ui.Res
import com.features.ui.authorization
import com.features.ui.dialog.DialogError
import com.features.ui.extension.CloseApp
import kotlinx.coroutines.flow.collect
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthPhoneScreen(
    viewModel: AuthViewModel,// = koinViewModel(),
    onNavigateToCodeInput: () -> Unit
) {
    val context = LocalPlatformContext.current
    val state by viewModel.state.collectAsState()


    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is AuthEffects.NavigateToCodeInput -> {
                    onNavigateToCodeInput()
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
                CloseApp(context)
            }
        )
    }
}