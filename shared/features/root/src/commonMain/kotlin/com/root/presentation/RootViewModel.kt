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
        val currentDestination = state.value.destination
        if (destination == currentDestination) return

        val effect = if (isClearStack) RootEffect.NavigateWithClearStack(destination)
        else RootEffect.Navigate(destination)

        updateState {
            it.copy(destination = destination)
        }

        sendEffect(effect)
    }

    private fun finishScreen() {
        updateState { it.copy(destination = null) }
        sendEffect(RootEffect.PopBackStack)
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