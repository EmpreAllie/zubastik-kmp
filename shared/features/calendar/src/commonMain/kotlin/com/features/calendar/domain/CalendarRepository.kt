package com.features.calendar.domain

import com.features.calendar.domain.model.CalendarDay
import com.features.calendar.domain.model.CalendarEvent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate

interface CalendarRepository {
    val allEvents: StateFlow<Map<LocalDate, List<CalendarEvent>>>

    fun getMonthDays(
        date: LocalDate,
        allEvents: Map<LocalDate, List<CalendarEvent>>,
    ): List<CalendarDay>

    fun saveEvent(
        date: LocalDate,
        newEvent: CalendarEvent,
    )

    fun deleteEvent(
        date: LocalDate,
        event: CalendarEvent,
    )
}
