package com.features.calendar.presentation.model

import com.features.base.presentation.model.BaseState
import com.features.calendar.domain.model.CalendarDay
import com.features.calendar.domain.model.CalendarEvent
import com.features.calendar.domain.model.CalendarEventStatus
import kotlinx.datetime.LocalDate

data class CalendarState(
    val isDialogVisible: Boolean = false,
    val isInAddEventMode: Boolean = false,
    val year: String = "1900",
    val eventDescription: String = "",
    val selectedMonthDate: LocalDate,
    val selectedDateForEdit: LocalDate? = null,
    val newEventStatus: CalendarEventStatus = CalendarEventStatus.VISITED_DOCTOR,
    val allEvents: Map<LocalDate, List<CalendarEvent>> = emptyMap(),
    val days: List<CalendarDay> = emptyList(),
    val selectedTime: Time = 12 andMinute 0,
) : BaseState(
        isLoading = false,
        error = null,
    )
