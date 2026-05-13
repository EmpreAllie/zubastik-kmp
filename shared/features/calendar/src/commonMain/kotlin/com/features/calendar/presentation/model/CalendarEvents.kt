package com.features.calendar.presentation.model

import com.features.calendar.domain.model.CalendarEvent
import com.features.calendar.domain.model.CalendarEventStatus
import kotlinx.datetime.LocalDate

sealed interface CalendarEvents {
    data object OnPrevMonthClick : CalendarEvents

    data object OnNextMonthClick : CalendarEvents

    data class OnDateClick(
        val date: LocalDate,
    ) : CalendarEvents

    data object OnDismissDialog : CalendarEvents

    data object OnAddEventClick : CalendarEvents

    data object OnSaveEvent : CalendarEvents

    data class OnTextChanged(
        val newText: String,
    ) : CalendarEvents

    data class OnStatusSelected(
        val newStatus: CalendarEventStatus,
    ) : CalendarEvents

    data class OnDeleteEvent(
        val event: CalendarEvent,
    ) : CalendarEvents

    data class OnEventHourChanged(
        val hour: Int,
    ) : CalendarEvents

    data class OnEventMinuteChanged(
        val minute: Int,
    ) : CalendarEvents

    data object OnCloseErrorDialog: CalendarEvents
}
