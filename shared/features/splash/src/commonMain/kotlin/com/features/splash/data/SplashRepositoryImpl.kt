package com.features.splash.data

import com.features.splash.domain.SplashRepository
//import com.russhwolf.settings.Settings

class SplashRepositoryImpl(
    //private val settings: Settings
): SplashRepository {
    override suspend fun isAuthenticated(): Boolean {
        val isUserAuthenticated = false

        return isUserAuthenticated
    }
}