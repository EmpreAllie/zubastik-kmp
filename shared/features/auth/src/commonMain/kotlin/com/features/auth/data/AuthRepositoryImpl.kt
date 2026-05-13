package com.features.auth.data

import com.core.data.infrastructure.KeyValueStorage
import com.features.auth.domain.AuthData
import com.features.auth.domain.AuthRepository
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.network.api.apis.AuthApi
import com.network.api.models.ApiV1AuthSendCodePostRequest
import com.network.api.models.VerifyCodeRequest
import com.network.data.exception.CustomResponseException
import com.network.domain.model.isConnectionException
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.IO
import kotlinx.coroutines.flow.Flow
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

    override fun sendPhoneNumberToServer(phone: String): Flow<Result<Unit, Error>> = flow {
        emit(Result.Loading)
        try {
            authApi.apiV1AuthSendCodePost(ApiV1AuthSendCodePostRequest(phone))
            emit(Result.Success(Unit))
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
            
            println("ZUB_DEBUG: access token: ${keyValueStorage.accessToken}")

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