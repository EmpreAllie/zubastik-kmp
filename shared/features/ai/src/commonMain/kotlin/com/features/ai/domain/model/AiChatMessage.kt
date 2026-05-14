package com.features.ai.domain.model

data class AiChatMessage(
    val id: String,
    val role: String,
    val content: String,
    val createdAt: String,
)
