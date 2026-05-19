package com.features.ai.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.unit.dp
import com.features.ai.presentation.model.AiEvents
import com.features.ai.presentation.model.AiState
import com.features.ai.ui.components.AiChatBubble
import com.features.ai.ui.components.AiChatHeader
import com.features.ai.ui.components.AiSendMessageElement
import com.features.ui.Res
import com.features.ui.ic_send_message
import com.features.ui.message
import com.features.ui.theme.MainTheme
import kotlinx.coroutines.launch
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AiScreenContent(
    state: AiState,
    onEvent: (AiEvents) -> Unit,
) {

    val lazyListState = rememberLazyListState()
    val coroutineScope = rememberCoroutineScope()

    LaunchedEffect(state.aiChatMessageList.size) {
        if (state.aiChatMessageList.isNotEmpty()) {
            coroutineScope.launch {
                lazyListState.animateScrollToItem(state.aiChatMessageList.size - 1)
            }
        }
    }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(10.dp)
    ) {
        AiChatHeader()

        Spacer(modifier = Modifier.height(10.dp))

        Column(
            modifier = Modifier
                .fillMaxSize()
                .clip(RoundedCornerShape(30.dp))
                .background(MainTheme.colors.white)
                .padding(20.dp)
        ) {
            LazyColumn(
                state = lazyListState,
                modifier = Modifier.weight(1f),
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                items(state.aiChatMessageList, key = { it.id }) { message ->
                    AiChatBubble(
                        modifier = Modifier.animateItem(),
                        curMessage = message
                    )
                }
            }

            Spacer(modifier = Modifier.height(8.dp))

            AiSendMessageElement(
                state = state,
                onEvent = onEvent
            )
        }
    }
}