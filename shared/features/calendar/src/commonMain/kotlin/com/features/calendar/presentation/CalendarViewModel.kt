package com.features.calendar.presentation

import androidx.lifecycle.viewModelScope
import com.core.data.utils.DateTimeManager
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error
import com.features.base.presentation.model.BaseViewModel
import com.features.calendar.domain.CalendarRepository
import com.features.calendar.domain.model.CalendarEvent
import com.features.calendar.domain.model.CalendarEventStatus
import com.features.calendar.presentation.model.CalendarEffects
import com.features.calendar.presentation.model.CalendarEvents
import com.features.calendar.presentation.model.CalendarState
import com.features.calendar.presentation.model.DefaultTimePickerConfig
import com.features.calendar.presentation.model.MonthChange
import com.features.calendar.presentation.model.andMinute
import com.features.calendar.presentation.model.format
import com.features.calendar.presentation.model.hour
import com.features.calendar.presentation.model.minute
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.coroutines.launch
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarViewModel(
    private val repository: CalendarRepository,
    dateTimeManager: DateTimeManager,
) : BaseViewModel<CalendarState, CalendarEvents, CalendarEffects>(
        CalendarState(
            selectedMonthDate = dateTimeManager.getCurrentLocalDate(),
        ),
    ) {
    init {

        updateState { it.copy(isLoading = true) }

        viewModelScope.launch {
            repository.loadEventsFromServer().collect { result ->
                handleResult(result) {}
            }
        }

        repository.allEvents
            .onEach { eventsMap ->
                val updatedDays =
                    repository.getMonthDays(
                        date = state.value.selectedMonthDate,
                        allEvents = eventsMap,
                    )

                updateState {
                    it.copy(
                        allEvents = eventsMap,
                        days = updatedDays,
                    )
                }
            }.flowOn(Dispatchers.Default)
            .launchIn(viewModelScope)
    }

    override fun onEvent(event: CalendarEvents) {
        when (event) {
            CalendarEvents.OnPrevMonthClick -> {
                changeMonth(MonthChange.PREVIOUS)
            }

            CalendarEvents.OnNextMonthClick -> {
                changeMonth(MonthChange.NEXT)
            }

            is CalendarEvents.OnDateClick -> {
                updateState {
                    it.copy(
                        isDialogVisible = true,
                        selectedDateForEdit = event.date,
                    )
                }
            }

            CalendarEvents.OnDismissDialog -> {
                updateState {
                    it.copy(
                        isDialogVisible = false,
                        isInAddEventMode = false,
                        selectedTime =
                            DefaultTimePickerConfig.CALENDAR_DEFAULT.hour andMinute
                                DefaultTimePickerConfig.CALENDAR_DEFAULT.minute,
                    )
                }
            }

            CalendarEvents.OnAddEventClick -> {
                updateState { it.copy(isInAddEventMode = true) }
            }

            CalendarEvents.OnSaveEvent -> {
                processSaveEvent()
            }

            is CalendarEvents.OnDeleteEvent -> {
                processDeleteEvent(event.event)
            }

            is CalendarEvents.OnTextChanged -> {
                updateState { it.copy(eventDescription = event.newText) }
            }

            is CalendarEvents.OnStatusSelected -> {
                updateState { it.copy(newEventStatus = event.newStatus) }
            }

            is CalendarEvents.OnEventHourChanged -> {
                updateState { it.copy(selectedTime = event.hour andMinute it.selectedTime.minute) }
            }

            is CalendarEvents.OnEventMinuteChanged -> {
                updateState { it.copy(selectedTime = it.selectedTime.hour andMinute event.minute) }
            }

            CalendarEvents.OnCloseErrorDialog -> { updateState { it.copy(error = null) }}
        }
    }

    private fun changeMonth(direction: MonthChange) {
        val currentDate = state.value.selectedMonthDate
        val nextDateForMonthChange =
            when (direction) {
                MonthChange.PREVIOUS -> currentDate.minus(1, DateTimeUnit.MONTH)
                MonthChange.NEXT -> currentDate.plus(1, DateTimeUnit.MONTH)
            }

        val updatedDays =
            repository.getMonthDays(
                date = nextDateForMonthChange,
                allEvents = repository.allEvents.value,
            )

        updateState {
            it.copy(
                selectedMonthDate = nextDateForMonthChange,
                days = updatedDays,
            )
        }
    }

    private fun processSaveEvent() {
        val date = state.value.selectedDateForEdit ?: return

        val formattedTime = state.value.selectedTime.format()

        val newEvent =
            CalendarEvent(
                time = formattedTime,
                description = state.value.eventDescription,
                status = state.value.newEventStatus,
            )

        viewModelScope.launch {
            repository.saveEvent(
                date = date,
                newEvent = newEvent
            ).collect { result ->

                when(result) {

                    Result.Loading -> updateState { it.copy(isLoading = true) }

                    is Result.Success -> {
                        updateState {
                            it.copy(
                                isInAddEventMode = false,
                                newEventStatus = CalendarEventStatus.VISITED_DOCTOR,
                                eventDescription = "",
                                selectedTime =
                                    DefaultTimePickerConfig.CALENDAR_DEFAULT.hour andMinute
                                            DefaultTimePickerConfig.CALENDAR_DEFAULT.minute,
                                isLoading = false
                            )
                        }
                    }

                    is Result.Failure -> updateState { it.copy(isLoading = false, error = result.error) }

                    Result.ConnectionError -> {
                        updateState {
                            it.copy(
                                isLoading = false,
                                error = Error.CONNECTION
                            )
                        }
                    }

                    else -> {}
                }
            }
        }
    }

    private fun processDeleteEvent(event: CalendarEvent) {
        val date = state.value.selectedDateForEdit ?: return

        viewModelScope.launch {
            repository.deleteEvent(
                date = date,
                event = event,
            ).collect { result ->

                handleResult(result) {}

            }
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
