package com.root.presentation

import com.features.base.domain.enum.Destination
import com.features.base.presentation.model.BaseViewModel
import com.root.presentation.model.RootEffect
import com.root.presentation.model.RootEvent
import com.root.presentation.model.RootState

class RootViewModel : BaseViewModel<RootState, RootEvent, RootEffect>(RootState()) {
    private val openScreens: MutableList<Destination> = mutableListOf()
    override fun onEvent(event: RootEvent) {
        when (event) {
            is RootEvent.OnSetScreen -> setDestination(
                destination = event.destination,
                arguments = event.arguments,
                isClearStack = event.isClearStack
            )

            is RootEvent.OnReplaceScreen -> replaceScreen(
                destination = event.destination,
                arguments = event.arguments,
            )

            RootEvent.OnClickBack -> finishScreen()
        }
    }


    private fun setDestination(
        destination: Destination,
        arguments: List<String> = emptyList(),
        isClearStack: Boolean,
    ) {
        val currentScreen = this.state.value.destination

        if (destination == currentScreen) return

        if (isClearStack) openScreens.clear()

        openScreens.add(destination)

        updateState { it.copy(destination = destination, arguments = arguments) }

        val effect = if (isClearStack) RootEffect.NavigateWithClearStack(destination.route)
        else RootEffect.Navigate(destination.route)

        sendEffect(effect)
    }

    private fun areThereOtherOpenScreens() = openScreens.size > 1
    private fun finishScreen() {
        updateState { it.copy(destination = null, isPopScreen = true) }
        sendEffect(RootEffect.PopBackStack)
    }
    private fun replaceScreen(
        destination: Destination,
        arguments: List<String> = emptyList()
    ) {
        val currentScreen = this.state.value.destination

        if (destination == currentScreen) return

        if (openScreens.isNotEmpty()) openScreens.removeAt(openScreens.size-1)

        openScreens.add(destination)

        updateState {
            it.copy(destination = destination, arguments = arguments,)
        }

        sendEffect(RootEffect.ReplaceScreen(destination.route))
    }
}