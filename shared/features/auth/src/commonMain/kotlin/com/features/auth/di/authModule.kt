package com.features.auth.di

import com.features.auth.data.AuthRepositoryImpl
import com.features.auth.domain.AuthRepository
import com.features.auth.domain.TimerRepository
import com.features.auth.presentation.AuthCodeViewModel
import com.features.auth.presentation.AuthPhoneViewModel
import com.features.auth.presentation.AuthWelcomeViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule: Module = module {

    single { TimerRepository }

    // ViewModel'и
    factoryOf(::AuthWelcomeViewModel)
    factoryOf(::AuthPhoneViewModel)
    factoryOf(::AuthCodeViewModel)

    // репозитории
    single { AuthRepositoryImpl(get()) } bind AuthRepository::class
}