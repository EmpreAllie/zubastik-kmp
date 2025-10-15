package com.root.presentation

import com.features.base.presentation.model.BaseViewModel
import com.features.base.domain.enum.Screen
import com.root.presentation.model.RootEvents
import com.root.presentation.model.RootState

class RootViewModel : BaseViewModel<RootState, RootEvents>(RootState()) {

    private val openScreens: MutableList<Screen> = mutableListOf()

    override fun onEvent(events: RootEvents) = when (events) {
        is RootEvents.OnSetScreen -> setScreen(
            screen = events.screen,
            arguments = events.arguments,
            isClearStack = events.isClearStack
        )
        RootEvents.ClearIsPop -> clearIsPop()
        RootEvents.OnClickBack -> finishScreen()
    }


    private fun setScreen(
        screen: Screen,
        arguments: List<String> = emptyList(),
        isClearStack: Boolean,
    ) {
        val currentScreen = this.state.value.screen

        if (screen == currentScreen) return

        updateState { it.copy(isClearStack = isClearStack) }

        if (isClearStack) openScreens.clear()

        openScreens.add(screen)

        updateState { it.copy(screen = screen, arguments = arguments) }
    }

    private fun areThereOtherOpenScreens() = openScreens.size > 1
    private fun finishScreen() = updateState { it.copy(screen = null, isPopScreen = true) }
    private fun clearIsPop() = updateState { it.copy(isPopScreen = false) }
}