package com.features.auth.data

import com.features.auth.domain.AuthData
import com.features.auth.domain.AuthRepository
import kotlinx.coroutines.delay
import com.features.base.domain.model.error.Error
import com.features.base.domain.Result
import com.features.base.domain.model.error.AuthErrorType
import com.network.domain.model.isConnectionException
import kotlinx.coroutines.flow.flow

class AuthRepositoryImpl: AuthRepository {
    override suspend fun sendPhoneNumberToServer(phone: String): Result<Unit, Error> {
        return try {
            delay(1000)
            // TODO обращение к серверу
            Result.Success(Unit)

        } catch (e: Exception) {
            Result.Failure(Error.CONNECTION)
        }
    }



    override fun verifyCode(phone: String, code: String) = flow {
        emit(Result.Loading)

        try {
            delay(1000)
            if (code == "1234") {
                val mockData = AuthData(
                    accessToken = "mock-access-token",
                    refreshToken = "mock-refresh-token"
                )
                emit(Result.Success(mockData))
            }
            else {
                emit(Error.AUTH(AuthErrorType.CODE).toResult())
            }

        }
        catch (e: Exception) {
            e.printStackTrace()

            val result = if (e.isConnectionException())
                Result.ConnectionError
            else
                Error.OTHER(e.message.orEmpty()).toResult()

            emit(result)
        }
    }
}