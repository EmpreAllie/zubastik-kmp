package com.features.calendar.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.features.calendar.presentation.CalendarViewModel
import com.features.calendar.ui.content.CalendarScreenContent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun CalendarScreen(viewModel: CalendarViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    CalendarScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
    )
}
