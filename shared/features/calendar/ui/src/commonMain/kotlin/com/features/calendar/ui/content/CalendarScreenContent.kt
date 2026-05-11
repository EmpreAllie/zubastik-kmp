package com.features.calendar.ui.content

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.features.calendar.presentation.model.CalendarEvents
import com.features.calendar.presentation.model.CalendarState
import com.features.calendar.ui.components.CalendarBottomLegend
import com.features.calendar.ui.components.CalendarDateGrid
import com.features.calendar.ui.components.CalendarHeader
import com.features.calendar.ui.components.CalendarMonthSelector
import com.features.calendar.ui.components.CalendarWeekDays
import com.features.calendar.ui.dialog.CalendarDialog
import com.features.ui.theme.MainTheme

@Composable
fun CalendarScreenContent(
    state: CalendarState,
    onEvent: (CalendarEvents) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .fillMaxSize()
                .padding(20.dp),
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(30.dp))
                    .background(MainTheme.colors.containerBackground)
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.SpaceBetween,
        ) {
            CalendarHeader()

            Spacer(modifier = Modifier.height(16.dp))

            CalendarMonthSelector(
                selectedMonthDate = state.selectedMonthDate,
                onPrevClick = { onEvent(CalendarEvents.OnPrevMonthClick) },
                onNextClick = { onEvent(CalendarEvents.OnNextMonthClick) },
            )

            Spacer(modifier = Modifier.height(32.dp))

            Column(
                modifier = Modifier.fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally,
            ) {
                CalendarWeekDays()

                Spacer(modifier = Modifier.height(16.dp))

                CalendarDateGrid(
                    days = state.days,
                    onDayClick = { date -> onEvent(CalendarEvents.OnDateClick(date)) },
                )
            }

            Spacer(modifier = Modifier.weight(1f))

            CalendarBottomLegend()
        }

        if (state.isDialogVisible && state.selectedDateForEdit != null) {
            CalendarDialog(
                state = state,
                onEvent = onEvent,
            )
        }
    }
}
