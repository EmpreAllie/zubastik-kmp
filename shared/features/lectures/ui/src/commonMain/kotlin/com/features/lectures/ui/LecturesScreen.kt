package com.features.lectures.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.repeatOnLifecycle
import com.features.base.domain.enum.Screen
import com.features.lectures.presentation.LecturesViewModel
import com.features.lectures.presentation.model.LecturesEffects
import com.features.lectures.presentation.model.LecturesEvents
import com.features.lectures.ui.content.LecturesScreenContent
import com.features.ui.LoadingContent
import com.features.ui.Res
import com.features.ui.dialog.DialogError
import com.features.ui.lectures
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun LecturesScreen(
    viewModel: LecturesViewModel = koinViewModel(),
    rootViewModel: RootViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsState()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel, lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    LecturesEffects.NavigateToDetail -> {
                        rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.LECTURE_LIST))
                        rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.LECTURE_DETAIL))
                    }
                }
            }
        }
    }

    LecturesScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )

    if (state.isLoading) {
        LoadingContent()
    }

    state.error?.let { error ->
        DialogError(
            title = stringResource(Res.string.lectures),
            error = error,
            onClose = { viewModel.onEvent(LecturesEvents.OnCloseErrorDialog) }
        )
    }
}