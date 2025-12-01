package com.root.presentation

import com.features.base.presentation.model.BaseViewModel
import com.features.base.domain.enum.Screen
import com.root.presentation.model.RootEffect
import com.root.presentation.model.RootEvent
import com.root.presentation.model.RootState

class RootViewModel : BaseViewModel<RootState, RootEvent, RootEffect>(RootState()) {
    private val openScreens: MutableList<Screen> = mutableListOf()
    override fun onEvent(event: RootEvent) {
        when (event) {
            is RootEvent.OnSetScreen -> setScreen(
                screen = event.screen,
                arguments = event.arguments,
                isClearStack = event.isClearStack
            )

            is RootEvent.OnReplaceScreen -> replaceScreen(
                screen = event.screen,
                arguments = event.arguments,
            )

            RootEvent.OnClickBack -> finishScreen()
        }
    }


    private fun setScreen(
        screen: Screen,
        arguments: List<String> = emptyList(),
        isClearStack: Boolean,
    ) {
        val currentScreen = this.state.value.screen

        if (screen == currentScreen) return

        if (isClearStack) openScreens.clear()

        openScreens.add(screen)

        updateState { it.copy(screen = screen, arguments = arguments) }

        val effect = if (isClearStack) RootEffect.NavigateWithClearStack(screen.name)
        else RootEffect.Navigate(screen.name)

        sendEffect(effect)
    }

    private fun areThereOtherOpenScreens() = openScreens.size > 1
    private fun finishScreen() {
        updateState { it.copy(screen = null, isPopScreen = true) }
        sendEffect(RootEffect.PopBackStack)
    }
    private fun replaceScreen(
        screen: Screen,
        arguments: List<String> = emptyList()
    ) {
        val currentScreen = this.state.value.screen

        if (screen == currentScreen) return

        if (openScreens.isNotEmpty()) openScreens.removeAt(openScreens.size-1)

        openScreens.add(screen)

        updateState {
            it.copy(
                screen = screen,
                arguments = arguments,
            )
        }

        sendEffect(RootEffect.ReplaceScreen(screen.name))
    }
}