package com.features.onboard.di

import com.features.onboard.presentation.OnboardingViewModel
import org.koin.core.module.Module
import org.koin.core.module.dsl.factoryOf
import org.koin.dsl.module

val onboardingModule: Module = module {

    // ViewModel
    factoryOf(::OnboardingViewModel)
}