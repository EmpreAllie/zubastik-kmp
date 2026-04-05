package com.features.onboard.ui.components.content.inner.screens.chat

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyListState
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.onboard.presentation.model.OnboardingState

@Composable
fun OnboardingChatContent(
    state: OnboardingState,
    onEvent: (OnboardingEvents) -> Unit,
    lazyListState: LazyListState,
) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        reverseLayout = true,
        state = lazyListState,
    ) {
        items(
            items = state.chatMessages.reversed(),
            key = { it.id },
        ) { message ->

            MessageItem(
                message = message,
                // state = state,
                // onEvent = onEvent
                onTextChanged = { newText ->
                    onEvent(OnboardingEvents.OnTextInputChanged(newText))
                },
                curStep = state.curStep,
                userTextInput = state.userTextInput,
                userName = state.userName,
                userAge = state.userAge,
            )
        }
    }
}
