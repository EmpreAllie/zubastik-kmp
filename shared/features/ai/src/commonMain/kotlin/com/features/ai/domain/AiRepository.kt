package com.features.ai.domain

import com.features.ai.domain.model.AiChatMessage
import kotlinx.coroutines.flow.Flow
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error

interface AiRepository {
    fun getHistory(): Flow<Result<List<AiChatMessage>, Error>>

    fun sendUserMessageToServer(userMessageText: String): Flow<Result<AiChatMessage, Error>>
}