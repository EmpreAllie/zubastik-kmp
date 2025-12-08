package com.features.auth.data

import com.features.auth.domain.AuthRepository
import kotlinx.coroutines.delay

class AuthRepositoryImpl: AuthRepository {
    override suspend fun sendPhoneNumberToServer(phone: String) {
        //TODO("Not yet implemented")
        delay(1000)
    }

    override suspend fun verifyCode(phone: String, code: String): Boolean {
        //TODO("Not yet implemented")
        delay(1000)

        return code == "1234"
    }
}