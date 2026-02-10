package com.features.onboard.presentation.model.utils

import org.jetbrains.compose.resources.StringResource

data class ChatMessage(
    val id: Int,
    val textRes: StringResource? = null,
    val simpleText: String? = null,
    val author: Author,
    val timeStamp: String
)
