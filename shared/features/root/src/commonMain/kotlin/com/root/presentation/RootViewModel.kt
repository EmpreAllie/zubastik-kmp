package com.root.presentation

import com.features.base.domain.enum.Destination
import com.features.base.presentation.model.BaseViewModel
import com.root.presentation.model.RootEffect
import com.root.presentation.model.RootEvent
import com.root.presentation.model.RootState

class RootViewModel : BaseViewModel<RootState, RootEvent, RootEffect>(RootState()) {

    override fun onEvent(event: RootEvent) {
        when (event) {

            is RootEvent.OnSetScreen -> setDestination(
                destination = event.destination,
                isClearStack = event.isClearStack
            )

            is RootEvent.OnReplaceScreen -> replaceScreen(event.destination)

            RootEvent.OnClickBack -> finishScreen()

        }
    }


    private fun setDestination(
        destination: Destination,
        isClearStack: Boolean,
    ) {
        val currentStack = if (isClearStack) emptyList() else state.value.screenStack
        val newStack = currentStack + destination

        updateState {
            it.copy(
                destination = destination,
                screenStack = newStack
            )
        }

        sendEffect(RootEffect.Navigate(destination))
    }


    private fun finishScreen() {
        val currentStack = state.value.screenStack
        if (currentStack.size <= 1) return

        val newStack = currentStack.dropLast(1)
        val newDestination = newStack.last()

        updateState {
            it.copy(
                destination = newDestination,
                screenStack = newStack
            )
        }

        sendEffect(RootEffect.Navigate(newDestination))
    }


    private fun replaceScreen(destination: Destination) {
        val currentScreen = this.state.value.destination
        if (destination == currentScreen) return

        updateState {
            it.copy(destination = destination)
        }

        sendEffect(RootEffect.ReplaceScreen(destination))
    }
}