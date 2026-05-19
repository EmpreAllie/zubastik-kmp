package com.features.ai.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.features.ai.presentation.AiViewModel
import com.features.ai.presentation.model.AiEvents
import com.features.ai.ui.content.AiScreenContent
import com.features.ui.LoadingContent
import com.features.ui.Res
import com.features.ui.ai
import com.features.ui.dialog.DialogError
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AiScreen(
    viewModel: AiViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

    AiScreenContent(
        state = state,
        onEvent = viewModel::onEvent
    )

    if (state.isLoading) {
        LoadingContent()
    }

    state.error?.let {  error ->
        DialogError(
            title = stringResource(Res.string.ai),
            error = error,
            onClose = {
                viewModel.onEvent(AiEvents.OnCloseErrorDialog)
            }
        )
    }
}