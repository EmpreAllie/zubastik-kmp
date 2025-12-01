package com.root.presentation.model


sealed interface RootEffect {
    data class Navigate(val route: String) : RootEffect
    data class NavigateWithClearStack(val route: String) : RootEffect
    data class ReplaceScreen(val route: String) : RootEffect
    object PopBackStack : RootEffect
}