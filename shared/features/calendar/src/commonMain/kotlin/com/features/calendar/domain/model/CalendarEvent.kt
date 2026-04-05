package com.features.calendar.domain.model

data class CalendarEvent(
    val time: String,
    val description: String,
    val status: CalendarEventStatus,
)
