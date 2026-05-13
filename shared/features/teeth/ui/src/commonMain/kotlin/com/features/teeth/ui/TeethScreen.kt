package com.features.teeth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.features.teeth.presentation.TeethViewModel
import com.features.teeth.presentation.model.TeethEvents
import com.features.teeth.ui.components.TeethScreenContent
import com.features.ui.LoadingContent
import com.features.ui.Res
import com.features.ui.dialog.DialogError
import com.features.ui.teethPanel
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TeethScreen(viewModel: TeethViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    TeethScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
    )

    if (state.isLoading) {
        LoadingContent()
    }

    state.error?.let {  error ->
        DialogError(
            title = stringResource(Res.string.teethPanel),
            error = error,
            onClose = {
                viewModel.onEvent(TeethEvents.OnCloseErrorDialog)
            }
        )
    }
}
