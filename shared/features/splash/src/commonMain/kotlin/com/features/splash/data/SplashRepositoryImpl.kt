package com.features.splash.data

import com.core.data.infrastructure.KeyValueStorage
import com.features.splash.domain.SplashRepository
import com.network.api.apis.AuthApi
import com.network.api.models.TokenPair

class SplashRepositoryImpl(
    private val keyValueStorage: KeyValueStorage,
    private val authApi: AuthApi,
): SplashRepository {
    override suspend fun isAuthenticated() = keyValueStorage.hasTokens()

    override suspend fun refreshToken(): Boolean {
        return try {
            val result = authApi.apiV1AuthRefreshTokenPost(
                TokenPair(
                    accessToken = keyValueStorage.accessToken.orEmpty(),
                    refreshToken = keyValueStorage.refreshToken.orEmpty()
                )
            ).body()
            keyValueStorage.accessToken = result.accessToken
            keyValueStorage.refreshToken = result.refreshToken
            true
        } catch(e: Exception) {
            keyValueStorage.clearTokens()
            false
        }
    }
}