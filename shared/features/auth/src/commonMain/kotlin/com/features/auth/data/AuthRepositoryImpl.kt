package com.features.auth.data

import com.features.auth.domain.AuthData
import com.features.auth.domain.AuthRepository
import kotlinx.coroutines.delay
import com.features.base.domain.model.Error
import com.features.base.domain.Result
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

    override suspend fun verifyCode(phone: String, code: String) = flow {
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
                emit(Result.Failure(Error.other("Неверный код")))
            }
        }
        catch(e: Exception) {
            emit(Result.ConnectionError)
        }
    }
/*
        return try {
            delay(1000)

            if (code == "1234") {
                val mockData = AuthData(
                    accessToken = "mock-access-token",
                    refreshToken = "mock-refresh-token"
                )

                Result.Success(mockData)
            } else {
                Result.Failure(Error.other("Неверный код"))
            }
        } catch (e: Exception) {
            Result.Failure(Error.CONNECTION)
        }
    }

 */
}