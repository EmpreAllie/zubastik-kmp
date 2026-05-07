package com.features.teeth.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.features.teeth.presentation.TeethViewModel
import com.features.teeth.ui.components.TeethScreenContent
import com.features.teeth.ui.mappanel.InteractiveTeethMap
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun TeethScreen(viewModel: TeethViewModel = koinViewModel()) {
    val state by viewModel.state.collectAsState()

    TeethScreenContent(
        state = state,
        onEvent = viewModel::onEvent,
    )
}
