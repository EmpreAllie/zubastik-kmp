package com.features.auth.domain

interface AuthRepository {
    suspend fun sendPhoneNumberToServer(phone: String)

    /**
     * @param phone Номер телефона, который надо отправить на сервер.
     * @param code Код, введенный пользователем.
     * @return true, если код верный, иначе false.
    */
    suspend fun verifyCode(phone: String, code: String): Boolean
}