package com.features.calendar.data

import com.features.calendar.domain.CalendarRepository
import com.features.calendar.domain.model.CalendarDay
import com.features.calendar.domain.model.CalendarEvent
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import kotlinx.datetime.isoDayNumber

class CalendarRepositoryImpl : CalendarRepository {
    private val _allEvents = MutableStateFlow<Map<LocalDate, List<CalendarEvent>>>(emptyMap())
    override val allEvents = _allEvents.asStateFlow()

    override fun getMonthDays(
        date: LocalDate,
        allEvents: Map<LocalDate, List<CalendarEvent>>,
    ): List<CalendarDay> {
        val daysInMonth = date.month.length(isLeap = isLeapYear(date.year))

        val firstDayOfMonth = LocalDate(date.year, date.month, 1)
        val emptyDaysInTheBeginning = firstDayOfMonth.dayOfWeek.isoDayNumber - 1

        val result = mutableListOf<CalendarDay>()

        repeat(emptyDaysInTheBeginning) {
            result.add(
                CalendarDay(date = null),
            )
        }

        for (day in 1..daysInMonth) {
            val currentDate = LocalDate(date.year, date.month, day)

            val dayStatuses =
                allEvents[currentDate]
                    ?.sortedBy { it.time }
                    ?.map { it.status } ?: emptyList()

            result.add(
                CalendarDay(
                    date = LocalDate(date.year, date.month, day),
                    statuses = dayStatuses,
                ),
            )
        }

        return result
    }

    override fun saveEvent(
        date: LocalDate,
        newEvent: CalendarEvent,
    ) {
        val map = _allEvents.value.toMutableMap()
        val dayEvents = map[date] ?: emptyList()
        map[date] = (dayEvents + newEvent).sortedBy { it.time }
        _allEvents.value = map
    }

    override fun deleteEvent(
        date: LocalDate,
        event: CalendarEvent,
    ) {
        val map = _allEvents.value.toMutableMap()
        val dayEvents = map[date] ?: emptyList()
        val updatedEvents = dayEvents - event

        if (updatedEvents.isEmpty()) {
            map.remove(date)
        } else {
            map[date] = updatedEvents
        }

        _allEvents.value = map
    }

    private fun Month.length(isLeap: Boolean): Int =
        when (this) {
            Month.FEBRUARY -> if (isLeap) 29 else 28
            Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30
            else -> 31
        }

    private fun isLeapYear(year: Int): Boolean = (year % 4 == 0 && year % 100 != 0) || (year % 400 == 0)
}
