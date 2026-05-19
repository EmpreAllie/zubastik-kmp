package com.features.splash.domain

interface SplashRepository {
    suspend fun isAuthenticated() : Boolean

    suspend fun refreshToken() : Boolean
}