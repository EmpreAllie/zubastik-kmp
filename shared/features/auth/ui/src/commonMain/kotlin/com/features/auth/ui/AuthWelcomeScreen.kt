package com.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.features.auth.domain.model.LoginType
import com.features.auth.presentation.AuthViewModel
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.ui.components.content.AuthWelcomeScreenContent
import com.features.base.domain.enum.Screen
import com.features.ui.Res
import com.features.ui.authorization
import com.features.ui.dialog.DialogError
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun AuthWelcomeScreen(
    viewModel: AuthViewModel = koinViewModel(),
    rootViewModel: RootViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.clearEffects()
        viewModel.effect.collect { effect ->
            when (effect) {
                AuthEffects.NavigateToBack -> rootViewModel.onEvent(RootEvent.OnClickBack)
                AuthEffects.NavigateToCodeInput -> rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.CONFIRM))
                AuthEffects.NavigateToMain -> rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.MAIN))
                is AuthEffects.NavigateToLogin -> {
                    val screen = when (effect.type) {
                        LoginType.PHONE -> Screen.PHONE
                        LoginType.YANDEX -> TODO("Yandex Login")
                    }

                    rootViewModel.onEvent(RootEvent.OnSetScreen(screen))
                }
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
            }
        )
    }
}
