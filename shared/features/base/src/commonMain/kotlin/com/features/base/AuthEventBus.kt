package com.features.base

import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow

object AuthEventBus {
    private val _event = MutableSharedFlow<AppEvent>()
    val event = _event.asSharedFlow()

    suspend fun send(event: AppEvent) {
        _event.emit(event)
    }
}