package com.features.splash.data

import com.features.splash.domain.SplashRepository

class SplashRepositoryImpl(
): SplashRepository {
    override suspend fun isAuthenticated() = false
}