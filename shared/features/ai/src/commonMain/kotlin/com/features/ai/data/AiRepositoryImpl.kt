package com.features.ai.data

import com.features.ai.domain.AiRepository
import com.features.ai.domain.model.AiChatMessage
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.network.api.apis.AiChatApi
import com.network.api.models.ChatMessageRequest
import com.network.api.models.GreetRequest
import com.network.data.exception.CustomResponseException
import com.network.domain.model.isConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlin.time.ExperimentalTime

class AiRepositoryImpl(
    private val aiChatApi: AiChatApi
): AiRepository {

    @OptIn(ExperimentalTime::class)
    override fun getHistory(): Flow<Result<List<AiChatMessage>, Error>> = flow {
        emit(Result.Loading)

        try {
            val response = aiChatApi.apiV1AiChatHistoryGet().body()

            val messages = response.map { msg ->
                AiChatMessage(
                    id = msg.ID.orEmpty(),
                    role = msg.role?.value.orEmpty(),
                    content = msg.content.orEmpty(),
                    createdAt = msg.createdAt?.toString().orEmpty()
                )
            }
            emit(Result.Success(messages))
        } catch (e: CustomResponseException) {
            emit(Error.OTHER(e.message.orEmpty()).toResult())

        } catch (e: Exception) {
            val result = if (e.isConnectionException())
                Result.ConnectionError
            else
                Error.OTHER(e.message.orEmpty()).toResult()

            emit(result)
        }
    }.flowOn(Dispatchers.IO)


    @OptIn(ExperimentalTime::class)
    override fun sendUserMessageToServer(
        userMessageText: String
    ): Flow<Result<List<AiChatMessage>, Error>> = flow {
        emit(Result.Loading)

        try {
            val response = aiChatApi.apiV1AiChatMessagePost(
                ChatMessageRequest(
                    message = userMessageText
                )
            ).body()

            val messages = response.map { msg ->
                AiChatMessage(
                    id = msg.ID.orEmpty(),
                    role = msg.role?.value.orEmpty(),
                    content = msg.content.orEmpty(),
                    createdAt = msg.createdAt?.toString().orEmpty()
                )
            }

            emit(Result.Success(messages))

        } catch (e: CustomResponseException) {
            emit(Error.OTHER(e.message.orEmpty()).toResult())

        } catch (e: Exception) {
            val result = if (e.isConnectionException())
                Result.ConnectionError
            else
                Error.OTHER(e.message.orEmpty()).toResult()

            emit(result)
        }
    }.flowOn(Dispatchers.IO)


    @OptIn(ExperimentalTime::class)
    override fun greetUser(language: String): Flow<Result<AiChatMessage, Error>> = flow {
        emit(Result.Loading)

        try {
            val response = aiChatApi.apiV1AiChatGreetPost(
                GreetRequest(
                    language = language
                )
            ).body()

            emit(
                Result.Success(
                    AiChatMessage(
                        id = response.ID.orEmpty(),
                        role = response.role?.value.orEmpty(),
                        content = response.content.orEmpty(),
                        createdAt = response.createdAt?.toString().orEmpty()
                    )
                )
            )

        } catch (e: CustomResponseException) {
            emit(Error.OTHER(e.message.orEmpty()).toResult())

        } catch (e: Exception) {
            val result = if (e.isConnectionException())
                Result.ConnectionError
            else
                Error.OTHER(e.message.orEmpty()).toResult()

            emit(result)
        }
    }.flowOn(Dispatchers.IO)
}