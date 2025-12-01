package com.core.di

import com.core.data.infrastructure.ConfigAppProvider
import com.core.data.model.ConfigParams
import com.core.data.infrastructure.KeyValueStorage
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module


@Suppress("LongMethod")
val coreModule: Module = module {
    singleOf(::ConfigAppProvider)
    singleOf(::KeyValueStorage)
    singleOf(::ConfigParams)
}