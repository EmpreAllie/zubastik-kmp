package com.features.calendar.presentation

import androidx.lifecycle.viewModelScope
import com.features.base.presentation.model.BaseViewModel
import com.features.calendar.data.CalendarRepositoryImpl
import com.features.calendar.domain.CalendarRepository
import com.features.calendar.domain.model.CalendarDay
import com.features.calendar.domain.model.CalendarEvent
import com.features.calendar.domain.model.CalendarEventStatus
import com.features.calendar.presentation.model.CalendarEffects
import com.features.calendar.presentation.model.CalendarEvents
import com.features.calendar.presentation.model.CalendarState
import com.features.calendar.presentation.model.MonthChange
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.launchIn
import kotlinx.coroutines.flow.onEach
import kotlinx.datetime.DateTimeUnit
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.TimeZone
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.minus
import kotlinx.datetime.plus
import kotlinx.datetime.todayIn
import kotlinx.datetime.yearMonth
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@OptIn(ExperimentalTime::class)
class CalendarViewModel(
    private val repository: CalendarRepository,
) : BaseViewModel<CalendarState, CalendarEvents, CalendarEffects>(
        CalendarState(
            selectedMonthDate =
                Clock.System.todayIn(TimeZone.currentSystemDefault()).run {
                    LocalDate(year, month, 1)
                },
        ),
    ) {
    init {
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
                        selectedHour = 12,
                        selectedMinute = 0,
                    )
                }
            }

            CalendarEvents.OnAddEventClick -> {
                updateState { it.copy(isInAddEventMode = true) }
            }

            CalendarEvents.OnSaveEvent -> {
                val date = state.value.selectedDateForEdit ?: return

                val formattedTime =
                    "${state.value.selectedHour.toString().padStart(2, '0')}:" +
                        state.value.selectedMinute
                            .toString()
                            .padStart(2, '0')

                val newEvent =
                    CalendarEvent(
                        time = formattedTime,
                        description = state.value.eventDescription,
                        status = state.value.newEventStatus,
                    )

                repository.saveEvent(
                    date = date,
                    newEvent = newEvent,
                )

                updateState {
                    it.copy(
                        isInAddEventMode = false,
                        newEventStatus = CalendarEventStatus.VISITED_DOCTOR,
                        eventDescription = "",
                        selectedHour = 12,
                        selectedMinute = 0,
                    )
                }
            }

            is CalendarEvents.OnDeleteEvent -> {
                val date = state.value.selectedDateForEdit ?: return
                repository.deleteEvent(
                    date = date,
                    event = event.event,
                )
            }

            is CalendarEvents.OnTextChanged -> {
                updateState { it.copy(eventDescription = event.newText) }
            }

            is CalendarEvents.OnStatusSelected -> {
                updateState { it.copy(newEventStatus = event.newStatus) }
            }

            is CalendarEvents.OnEventHourChanged -> {
                updateState { it.copy(selectedHour = event.hour) }
            }

            is CalendarEvents.OnEventMinuteChanged -> {
                updateState { it.copy(selectedMinute = event.minute) }
            }
        }
    }

    private fun changeMonth(direction: MonthChange) {
        val currentDate = state.value.selectedMonthDate
        val nextDateForMonthChange =
            when (direction) {
                MonthChange.PREVIOUS -> currentDate.minus(1, DateTimeUnit.MONTH)
                MonthChange.NEXT -> currentDate.plus(1, DateTimeUnit.MONTH)
            }

        updateState { it.copy(selectedMonthDate = nextDateForMonthChange) }

        val updatedDays =
            repository.getMonthDays(
                date = nextDateForMonthChange,
                allEvents = repository.allEvents.value,
            )

        updateState { it.copy(days = updatedDays) }
    }
}
