package com.root.presentation.model

import com.features.base.domain.enum.Destination

sealed interface RootEvent {
    data class OnSetScreen(val destination: Destination, val arguments: List<String> = emptyList(), val isClearStack: Boolean = false): RootEvent
    data class OnReplaceScreen(val destination: Destination, val arguments: List<String> = emptyList()): RootEvent
    data object OnClickBack: RootEvent
}