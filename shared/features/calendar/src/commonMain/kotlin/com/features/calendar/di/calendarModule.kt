package com.features.calendar.di

import com.features.calendar.data.CalendarRepositoryImpl
import com.features.calendar.domain.CalendarRepository
import com.features.calendar.presentation.CalendarViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val calendarModule: Module =
    module {
        factoryOf(::CalendarViewModel)
        singleOf(::CalendarRepositoryImpl) bind CalendarRepository::class
    }
