package com.root.presentation.model

import com.features.base.domain.enum.Screen

sealed interface RootEvent {
    data class OnSetScreen(val screen: Screen, val arguments: List<String> = emptyList(), val isClearStack: Boolean = false): RootEvent
    data class OnReplaceScreen(val screen: Screen, val arguments: List<String> = emptyList()): RootEvent
    data object OnClickBack: RootEvent
}