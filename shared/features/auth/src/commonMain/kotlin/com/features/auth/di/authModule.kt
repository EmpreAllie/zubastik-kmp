package com.features.auth.di

import com.features.auth.data.AuthRepositoryImpl
import com.features.auth.domain.AuthRepository
import com.features.auth.presentation.AuthViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val authModule: Module = module {
    factoryOf(::AuthViewModel)
    singleOf(::AuthRepositoryImpl) bind AuthRepository::class
}