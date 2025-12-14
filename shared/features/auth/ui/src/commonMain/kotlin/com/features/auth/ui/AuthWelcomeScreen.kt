package com.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import com.features.auth.presentation.AuthViewModel
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.ui.components.AuthWelcomeScreenContent
import com.features.base.domain.enum.Graph
import com.features.base.domain.enum.Screen
import com.features.ui.Res
import com.features.ui.authorization
import com.features.ui.dialog.DialogError
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.getKoin
import org.koin.compose.viewmodel.koinViewModel
import org.koin.core.qualifier.named


@Composable
fun AuthWelcomeScreen(
    //viewModel: AuthViewModel = koinViewModel(),
    //onNavigateToPhoneInput: () -> Unit,
    //onNavigateToYandexLogin: () -> Unit
) {

    val viewModel: AuthViewModel = koinViewModel(qualifier = named(Graph.AUTH.route))
    val rootViewModel: RootViewModel = koinViewModel()
    val state by viewModel.state.collectAsState()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                // Когда AuthViewModel говорит "перейти к вводу телефона"...
                is AuthEffects.NavigateToPhoneInput -> {
                    // ...мы говорим RootViewModel "установи экран ввода телефона".
                    rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.PHONE))
                }
                is AuthEffects.NavigateToYandexLogin -> {
                    // TODO: Реализовать логику для Яндекс логина,
                    // например, rootViewModel.onEvent(RootEvent.OnOpenYandexAuth)
                }
            }
        }
    }
    /*
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
    }*/

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
