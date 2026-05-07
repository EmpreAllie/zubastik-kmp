package com.root.di

import com.core.di.coreModule
import com.core.di.platformModule
import com.database.di.databaseModule
import com.features.auth.di.authModule
import com.features.calendar.di.calendarModule
import com.features.onboard.di.onboardingModule
import com.features.splash.di.splashModule
import com.features.teeth.di.teethModule
import com.network.di.networkModule
import org.koin.core.context.startKoin
import org.koin.dsl.KoinAppDeclaration

fun startKoin(koinAppDeclaration: KoinAppDeclaration) {
    startKoin {
        koinAppDeclaration()
        modules(
            rootModule,
            coreModule,
            databaseModule,
            platformModule,
            splashModule,
            networkModule,
            authModule,
            onboardingModule,
            teethModule,
            calendarModule,
        )
    }
}
