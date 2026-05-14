package com.features.ai.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import com.features.ai.presentation.AiViewModel
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AiScreen(
    viewModel: AiViewModel = koinViewModel()
) {
    val state by viewModel.state.collectAsState()

}