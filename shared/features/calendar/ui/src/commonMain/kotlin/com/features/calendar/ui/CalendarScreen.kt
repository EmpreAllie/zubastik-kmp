package com.features.calendar.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.features.calendar.presentation.CalendarViewModel
import com.features.calendar.presentation.model.CalendarEvents
import com.features.calendar.ui.content.CalendarScreenContent
import com.features.ui.LoadingContent
import com.features.ui.Res
import com.features.ui.calendar
import com.features.ui.dialog.DialogError
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CalendarScreen(viewModel: CalendarViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    CalendarScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
    )

    if (state.isLoading) {
        LoadingContent()
    }

    state.error?.let {  error ->
        DialogError(
            title = stringResource(Res.string.calendar),
            error = error,
            onClose = {
                viewModel.onEvent(CalendarEvents.OnCloseErrorDialog)
            }
        )
    }
}
