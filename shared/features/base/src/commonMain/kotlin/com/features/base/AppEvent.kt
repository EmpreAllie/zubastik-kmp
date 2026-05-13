package com.features.base

sealed interface AppEvent {
    data object Logout: AppEvent
}