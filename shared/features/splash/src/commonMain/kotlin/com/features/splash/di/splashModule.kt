package com.features.splash.di

import com.features.splash.data.SplashRepositoryImpl
import com.features.splash.domain.SplashRepository
import com.features.splash.presentation.SplashViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val splashModule: Module = module {
    factoryOf(::SplashViewModel)
    singleOf(::SplashRepositoryImpl) bind SplashRepository::class
}