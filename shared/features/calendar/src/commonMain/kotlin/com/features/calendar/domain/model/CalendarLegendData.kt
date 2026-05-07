package com.features.calendar.domain.model

import androidx.compose.ui.graphics.Color

data class CalendarLegendData(
    val color: Color,
    val text: String,
    val isSplit: Boolean = false,
)
