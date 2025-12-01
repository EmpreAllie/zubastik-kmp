package com.core.di

import android.content.Context
import com.core.data.utils.NativeHost
import com.russhwolf.settings.Settings
import com.russhwolf.settings.SharedPreferencesSettings
import org.koin.android.ext.koin.androidContext
import org.koin.core.module.Module
import org.koin.core.module.dsl.singleOf
import org.koin.dsl.module

actual val platformModule: Module = module {
    single<Settings> {
        SharedPreferencesSettings(
            androidContext().getSharedPreferences("app", Context.MODE_PRIVATE)
        )
    }

    singleOf(::NativeHost)
}