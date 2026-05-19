package com.features.ai.di

import com.features.ai.data.AiRepositoryImpl
import com.features.ai.domain.AiRepository
import com.features.ai.presentation.AiViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val aiModule: Module =
    module {
        factoryOf(::AiViewModel)
        singleOf(::AiRepositoryImpl) bind AiRepository::class
    }