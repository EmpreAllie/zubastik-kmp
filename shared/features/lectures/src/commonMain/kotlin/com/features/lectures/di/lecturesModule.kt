package com.features.lectures.di

import com.features.lectures.data.LecturesRepositoryImpl
import com.features.lectures.domain.LecturesRepository
import com.features.lectures.presentation.LectureDetailViewModel
import com.features.lectures.presentation.LecturesViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val lecturesModule: Module =
    module {
        factoryOf(::LecturesViewModel)
        factoryOf(::LectureDetailViewModel)
        singleOf(::LecturesRepositoryImpl) bind LecturesRepository::class
    }