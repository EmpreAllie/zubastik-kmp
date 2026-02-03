package com.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.features.auth.presentation.AuthPhoneViewModel
import com.features.auth.presentation.model.AuthEffects
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.ui.components.content.AuthPhoneScreenContent
import com.features.base.domain.enum.Screen
import com.features.ui.Res
import com.features.ui.authorization
import com.features.ui.dialog.DialogError
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AuthPhoneScreen(
    viewModel: AuthPhoneViewModel = koinViewModel(),
    rootViewModel: RootViewModel = koinViewModel(),
) {

    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel, lifecycleOwner.lifecycle) {

        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->

                when (effect) {
                    AuthEffects.NavigateBack -> {
                        rootViewModel.onEvent(RootEvent.OnClickBack)
                    }

                    is AuthEffects.NavigateToCodeInput -> {
                        /*
                        val phone = effect.phone
                        val route = Screen.CODE.route.replace("{phone}", phone)
                        */
                        rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.CODE))
                        //rootViewModel.onEvent(RootEvent.OnSetScreen(route))
                    }
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