package com.features.calendar.presentation.model

import com.features.base.presentation.model.BaseState
import com.features.calendar.domain.model.CalendarDay
import com.features.calendar.domain.model.CalendarEvent
import com.features.calendar.domain.model.CalendarEventStatus
import kotlinx.datetime.LocalDate

data class CalendarState(
    val year: String = "1900",
    val selectedMonthDate: LocalDate,
    val selectedDateForEdit: LocalDate? = null,
    val allEvents: Map<LocalDate, List<CalendarEvent>> = emptyMap(),
    val days: List<CalendarDay> = emptyList(),
    val isDialogVisible: Boolean = false,
    val isInAddEventMode: Boolean = false,
    val eventDescription: String = "",
    val newEventStatus: CalendarEventStatus = CalendarEventStatus.VISITED_DOCTOR,
    val selectedHour: Int = 12,
    val selectedMinute: Int = 0,
) : BaseState(
        isLoading = false,
        error = null,
    )
