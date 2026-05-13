package com.features.calendar.domain

import com.features.calendar.domain.model.CalendarDay
import com.features.calendar.domain.model.CalendarEvent
import kotlinx.coroutines.flow.StateFlow
import kotlinx.datetime.LocalDate
import kotlinx.coroutines.flow.Flow
import com.features.base.domain.Result
import com.features.base.domain.model.error.Error

interface CalendarRepository {
    val allEvents: StateFlow<Map<LocalDate, List<CalendarEvent>>>

    fun getMonthDays(
        date: LocalDate,
        allEvents: Map<LocalDate, List<CalendarEvent>>,
    ): List<CalendarDay>

    fun saveEvent(
        date: LocalDate,
        newEvent: CalendarEvent,
    ): Flow<Result<CalendarEvent, Error>>

    fun deleteEvent(
        date: LocalDate,
        event: CalendarEvent,
    ): Flow<Result<Unit, Error>>

    fun loadEventsFromServer(): Flow<Result<Unit, Error>>
}
