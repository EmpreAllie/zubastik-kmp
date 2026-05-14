package com.features.ai.data

import com.features.ai.domain.AiRepository
import com.features.ai.domain.model.AiChatMessage
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.network.api.apis.AiChatApi
import com.network.data.exception.CustomResponseException
import com.network.domain.model.isConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn

class AiRepositoryImpl(
    private val aiChatApi: AiChatApi
): AiRepository {
    override fun getHistory(): Flow<Result<List<AiChatMessage>, Error>> = flow {
        emit(Result.Loading)

        try {

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


    override fun sendUserMessageToServer(
        userMessageText: String
    ): Flow<Result<AiChatMessage, Error>> = flow {
        emit(Result.Loading)

        try {

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