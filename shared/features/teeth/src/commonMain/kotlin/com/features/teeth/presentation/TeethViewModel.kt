package com.features.teeth.presentation

import androidx.lifecycle.viewModelScope
import com.features.base.presentation.model.BaseViewModel
import com.features.teeth.domain.TeethRepository
import com.features.teeth.presentation.model.TeethEffects
import com.features.teeth.presentation.model.TeethEvents
import com.features.teeth.presentation.model.TeethState
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error

class TeethViewModel(
    private val repository: TeethRepository
): BaseViewModel<TeethState, TeethEvents, TeethEffects>(TeethState()) {

    init {
        updateState { it.copy(isLoading = true) }

        viewModelScope.launch {
            repository.loadTeethFromServer().collect { result ->
                handleResult(result) {}
            }
        }

        repository.teeth
            .onEach { newTeethList ->
                onEvent(TeethEvents.OnTeethUpdated(newTeethList))
            }
            .launchIn(viewModelScope)
    }

    override fun onEvent(event: TeethEvents) {
        when(event) {
            is TeethEvents.OnToothClicked -> {
                updateState {
                    it.copy(
                        selectedToothId = event.id
                    )
                }
            }

            is TeethEvents.OnTeethUpdated -> {
                updateState {
                    it.copy(
                        teeth = event.teeth
                    )
                }
            }

            TeethEvents.OnEditClicked -> {
                updateState {
                    it.copy(
                        isEditDialogVisible = true
                    )
                }
            }


            TeethEvents.OnDismissDialog -> {
                updateState {
                    it.copy(
                        isEditDialogVisible = false
                    )
                }
            }


            is TeethEvents.OnToothStatusChanged -> {
                repository.updateToothStatus(
                    id = event.toothId,
                    newStatus = event.status,
                    note = event.note
                )
                updateState {
                    it.copy(
                        isEditDialogVisible = false
                    )
                }
            }

            TeethEvents.OnCloseErrorDialog -> updateState { it.copy(error = null) }

        }
    }

    private fun handleResult(
        result: Result<Unit, Error>,
        onError: () -> Unit
    ) {

        when(result) {
            Result.Loading -> updateState { it.copy(isLoading = true) }
            is Result.Success -> updateState { it.copy(isLoading = false) }

            is Result.Failure -> {
                updateState { it.copy(isLoading = false, error = result.error) }
                onError()
            }

            Result.ConnectionError -> {
                updateState {
                    it.copy(
                        isLoading = false,
                        error = Error.CONNECTION
                    )
                }
                onError()
            }

            else -> {}
        }
    }
}