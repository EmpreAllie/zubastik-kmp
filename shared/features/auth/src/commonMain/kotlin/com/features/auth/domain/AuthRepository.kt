package com.features.auth.domain

import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import kotlinx.coroutines.flow.Flow

data class AuthData(
    val accessToken: String,
    val refreshToken: String,
    val isNewUser: Boolean
)

interface AuthRepository {

    // номер телефона, который хранится в памяти, т.к. AuthRepositoryImpl - это синглтон
    fun setPhone(phone: String)
    fun getPhone(): String?

    // возвращаем Flow
    fun sendPhoneNumberToServer(phone: String): Flow<Result<Unit, Error>>


    // возвращаем Flow из объектов Result.Loading, .Success, .Failure
    fun verifyCode(phone: String, code: String): Flow<Result<AuthData, Error>>

}