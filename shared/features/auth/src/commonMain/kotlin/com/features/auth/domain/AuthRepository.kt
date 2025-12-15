package com.features.auth.domain

import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import kotlinx.coroutines.flow.Flow

data class AuthData(val accessToken: String, val refreshToken: String)

interface AuthRepository {

    // возвращаем Result.Success(Unit), что означает "просто успех/неудача, без данных"
    suspend fun sendPhoneNumberToServer(phone: String): Result<Unit, Error>

    // возвращаем Flow из объектов Result.Loading, .Success, .Failure
    suspend fun verifyCode(phone: String, code: String): Flow<Result<AuthData, Error>>

}