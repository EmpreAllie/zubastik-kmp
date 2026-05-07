package com.features.teeth.di

import com.features.teeth.data.TeethRepositoryImpl
import com.features.teeth.domain.TeethRepository
import com.features.teeth.presentation.TeethViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val teethModule: Module = module {
    factoryOf(::TeethViewModel)
    singleOf(::TeethRepositoryImpl) bind TeethRepository::class
}