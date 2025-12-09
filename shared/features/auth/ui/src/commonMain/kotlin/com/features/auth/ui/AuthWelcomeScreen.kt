package com.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import coil3.compose.LocalPlatformContext
import com.features.auth.presentation.AuthViewModel
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.ui.components.AuthWelcomeScreenContent
import com.features.ui.Res
import com.features.ui.authorization
import com.features.ui.dialog.DialogError
import com.features.ui.extension.CloseApp
import com.root.presentation.RootViewModel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun AuthWelcomeScreen(
    viewModel: AuthViewModel,// = koinViewModel(),
    onNavigateToPhoneInput: () -> Unit,
    onNavigateToYandexLogin: () -> Unit
) {
    // доступ к системному API
    val context = LocalPlatformContext.current

    // AuthWelcomeScreen подписывается на State, чтобы получать из него изменения
    val state by viewModel.state.collectAsState()

    // собираем и слушаем все возможные эффекты из AuthViewModel
    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when(effect) {
                is AuthEffects.NavigateToPhoneInput -> onNavigateToPhoneInput()
                is AuthEffects.NavigateToYandexLogin -> onNavigateToYandexLogin()
            }
        }
    }

    // наполнение (UI) экрана
    // передаем туда состояние и обработчик событий, чтобы получать их обратно
    AuthWelcomeScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )

    // обработчик ошибок
    state.error?.let { error ->
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
