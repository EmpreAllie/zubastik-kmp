package com.features.splash.data

import com.core.data.infrastructure.KeyValueStorage
import com.features.splash.domain.SplashRepository

class SplashRepositoryImpl(
    private val keyValueStorage: KeyValueStorage
): SplashRepository {
    override suspend fun isAuthenticated() = keyValueStorage.hasTokens()
}