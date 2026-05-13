package com.features.calendar.domain.model

data class CalendarEvent(
    val id: String = "",
    val time: String,
    val description: String,
    val status: CalendarEventStatus,
)
