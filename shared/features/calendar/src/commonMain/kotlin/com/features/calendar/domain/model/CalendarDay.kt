package com.features.calendar.domain.model

import com.features.calendar.domain.model.CalendarEventStatus
import kotlinx.datetime.LocalDate

data class CalendarDay(
    val date: LocalDate?,
    // val status: CalendarEventStatus? = null,
    val statuses: List<CalendarEventStatus> = emptyList(),
)
