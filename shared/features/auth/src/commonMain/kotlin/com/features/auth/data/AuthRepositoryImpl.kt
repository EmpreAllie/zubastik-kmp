package com.features.auth.data

import com.core.data.infrastructure.KeyValueStorage
import com.features.auth.domain.AuthData
import com.features.auth.domain.AuthRepository
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.network.api.apis.AuthApi
import com.network.api.models.ApiV1AuthSendCodePostRequest
import com.network.api.models.VerifyCodeRequest
import com.network.domain.model.isConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext

class AuthRepositoryImpl(
    private val authApi: AuthApi,
    private val keyValueStorage: KeyValueStorage
) : AuthRepository {

    private var phone: String? = null

    override fun setPhone(phone: String) {
        this.phone = phone
    }

    override fun getPhone(): String? {
        return this.phone
    }

    override suspend fun sendPhoneNumberToServer(phone: String): Result<Unit, Error> {

        return withContext(Dispatchers.IO) {
            return@withContext try {

                // обращение к серверу
                 authApi.apiV1AuthSendCodePost(ApiV1AuthSendCodePostRequest(phone))

                Result.Success(Unit)
            }
            catch (e: Exception) {
                Result.Failure(Error.CONNECTION)
            }
        }

    }



    override fun verifyCode(phone: String, code: String) = flow {
        emit(Result.Loading)

        try {

            val response = authApi.apiV1AuthVerifyCodePost(
                VerifyCodeRequest(
                    phone = phone,
                    code = code
                )
            ).body()

            val authData = AuthData(
                accessToken = response.accessToken.orEmpty(),
                refreshToken = response.refreshToken.orEmpty(),
                isNewUser = response.isNewUser ?: false
            )

            keyValueStorage.accessToken = authData.accessToken
            keyValueStorage.refreshToken = authData.refreshToken

            emit(Result.Success(authData))
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