package com.root.presentation.model

import com.features.base.domain.enum.Destination


sealed interface RootEffect {
    data class Navigate(val destination: Destination) : RootEffect
    data class NavigateWithClearStack(val destination: Destination) : RootEffect
    data class ReplaceScreen(val destination: Destination) : RootEffect
    data object PopBackStack : RootEffect
}