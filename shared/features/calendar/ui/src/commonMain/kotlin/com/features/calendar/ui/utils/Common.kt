package com.features.calendar.ui.utils

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import com.features.calendar.domain.model.CalendarEventStatus
import com.features.ui.theme.MainTheme

@Composable
fun CalendarEventStatus.getEventColor(): Color =
    when (this) {
        CalendarEventStatus.VISITED_DOCTOR -> MainTheme.colors.eventBlue
        CalendarEventStatus.CONDITION_CHANGE_PROBLEM -> MainTheme.colors.eventOrange
        CalendarEventStatus.CONDITION_CHANGE_SOLUTION -> MainTheme.colors.eventLightBlue
        CalendarEventStatus.BRUSH_CHANGE -> MainTheme.colors.eventTiffany
        CalendarEventStatus.PLANNED_VISIT -> MainTheme.colors.eventRed
    }
