package com.features.onboard.di

import com.features.onboard.data.OnboardingRepositoryImpl
import com.features.onboard.domain.OnboardingRepository
import com.features.onboard.presentation.OnboardingViewModel
import com.features.teeth.presentation.TeethViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.bind
import org.koin.dsl.module

val onboardingModule: Module =
    module {
        factoryOf(::OnboardingViewModel)
        factoryOf(::TeethViewModel)
        singleOf(::OnboardingRepositoryImpl) bind OnboardingRepository::class
    }
