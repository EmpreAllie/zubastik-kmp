package com.features.onboard.ui.components.content.inner

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.features.onboard.presentation.model.OnboardingState
import androidx.compose.foundation.lazy.items
import com.features.onboard.presentation.model.OnboardingEvents

@Composable
fun OnboardingChatContent(
    state: OnboardingState,
    onEvent: (OnboardingEvents) -> Unit
) {
    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        reverseLayout = true
    ) {
        items(
            items = state.chatMessages.reversed(),
            key = { it.id },
        ) { message ->

            MessageItem(
                message = message,
                state = state,
                onEvent = onEvent
            )

        }
    }
}