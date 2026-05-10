package com.core.data.infrastructure

import com.russhwolf.settings.Settings
import com.russhwolf.settings.nullableBoolean
import com.russhwolf.settings.nullableString
import com.russhwolf.settings.string

class KeyValueStorage(private val settings: Settings) {
    var firstLaunch by settings.nullableBoolean("first_launch")

    var accessToken by settings.nullableString("access_token")
    var refreshToken by settings.nullableString("refresh_token")

    fun clearTokens() {
        accessToken = null
        refreshToken = null
    }

    fun hasTokens() = !(accessToken.isNullOrBlank() && refreshToken.isNullOrBlank())
}
