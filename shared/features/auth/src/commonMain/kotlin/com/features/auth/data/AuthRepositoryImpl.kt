package com.features.auth.data

import com.features.auth.domain.AuthData
import com.features.auth.domain.AuthRepository
import kotlinx.coroutines.delay
import com.features.base.domain.model.error.Error
import com.features.base.domain.Result
import com.features.base.domain.model.error.AuthErrorType
import com.network.api.apis.UserApi
import com.network.domain.model.isConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    userApi: UserApi
) : AuthRepository {

    override var phone: String? = null

    override suspend fun sendPhoneNumberToServer(phone: String): Result<Unit, Error> {

        return withContext(Dispatchers.IO) {
            return@withContext try {
                delay(1000)
                // TODO обращение к серверу

                Result.Success(Unit)


            } catch (e: Exception) {
                Result.Failure(Error.CONNECTION)
            }
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
    }.flowOn(Dispatchers.IO)
}