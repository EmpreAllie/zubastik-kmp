package com.features.lectures.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.unit.dp
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.repeatOnLifecycle
import coil3.compose.SubcomposeAsyncImage
import com.features.lectures.presentation.LectureDetailViewModel
import com.features.lectures.presentation.model.LectureDetailEffects
import com.features.lectures.presentation.model.LectureDetailEvents
import com.features.ui.Res
import com.features.ui.ic_back_arrow
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import org.jetbrains.compose.resources.painterResource
import org.koin.compose.viewmodel.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LectureDetailScreen(
    viewModel: LectureDetailViewModel = koinViewModel(),
    rootViewModel: RootViewModel = koinViewModel(),
) {
    val state by viewModel.state.collectAsStateWithLifecycle()
    val lifecycleOwner = LocalLifecycleOwner.current

    LaunchedEffect(viewModel, lifecycleOwner.lifecycle) {
        lifecycleOwner.lifecycle.repeatOnLifecycle(Lifecycle.State.STARTED) {
            viewModel.effect.collect { effect ->
                when (effect) {
                    LectureDetailEffects.NavigateBack ->
                        rootViewModel.onEvent(RootEvent.OnClickBack)
                }
            }
        }
    }

    val article = state.article ?: return

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(article.title) },
                navigationIcon = {
                    IconButton(
                        onClick = {
                            viewModel.onEvent(LectureDetailEvents.OnBackClicked)
                        }) {
                            Icon(
                                painter = painterResource(Res.drawable.ic_back_arrow),
                                contentDescription = null,
                            )
                        }
                }
            )
        }
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .verticalScroll(rememberScrollState())
        ) {
            if (article.imageUrl.isNotBlank()) {
                SubcomposeAsyncImage(
                    model = article.imageUrl,
                    contentDescription = null,
                    contentScale = ContentScale.Crop,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(240.dp),
                )
            }
            Text(
                text = article.content,
                style = MaterialTheme.typography.bodyLarge,
                modifier = Modifier.padding(16.dp),
            )
        }
    }
}