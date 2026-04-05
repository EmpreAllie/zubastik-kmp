package com.features.calendar.ui.dialog

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.Dialog
import com.features.calendar.domain.model.CalendarEvent
import com.features.calendar.domain.model.CalendarEventStatus
import com.features.calendar.presentation.model.CalendarEvents
import com.features.calendar.presentation.model.CalendarState
import com.features.calendar.ui.dialog.components.CalendarDialogHeader
import com.features.calendar.ui.dialog.components.CalendarEventCreationView
import com.features.calendar.ui.dialog.components.CalendarEventsList
import com.features.calendar.ui.utils.getEventColor
import com.features.calendar.ui.utils.toFullStringResource
import com.features.calendar.ui.utils.toGenitiveStringResource
import com.features.ui.Res
import com.features.ui.add
import com.features.ui.button.MainButton
import com.features.ui.events
import com.features.ui.ic_cross
import com.features.ui.noEventsYet
import com.features.ui.theme.MainTheme
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarDialog(
    state: CalendarState,
    onEvent: (CalendarEvents) -> Unit,
) {
    val selectedDate = state.selectedDateForEdit ?: return
    val events = state.allEvents[state.selectedDateForEdit] ?: emptyList()

    Dialog(
        onDismissRequest = { onEvent(CalendarEvents.OnDismissDialog) },
    ) {
        Column(
            modifier =
                Modifier
                    .fillMaxWidth()
                    .clip(RoundedCornerShape(40.dp))
                    .background(MainTheme.colors.white)
                    .padding(24.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
        ) {
            // Загловок с датой и днём недели
            CalendarDialogHeader(
                date = selectedDate,
                onDismiss = { onEvent(CalendarEvents.OnDismissDialog) },
            )

            if (!state.isInAddEventMode) {
                CalendarEventsList(
                    events = events,
                    onDeleteEvent = { event ->
                        onEvent(CalendarEvents.OnDeleteEvent(event))
                    },
                )

                if (events.isNotEmpty()) {
                    Spacer(modifier = Modifier.height(24.dp))
                }

                MainButton(
                    modifier =
                        Modifier
                            .width(100.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .align(Alignment.CenterHorizontally),
                    text = stringResource(Res.string.add),
                    textStyle = MainTheme.typography.calendarDialog.addButtonText,
                    contentColor = MainTheme.colors.black,
                    backgroundColor = MainTheme.colors.button,
                    onClick = {
                        onEvent(CalendarEvents.OnAddEventClick)
                    },
                )
            } else {
                CalendarEventCreationView(
                    eventDescription = state.eventDescription,
                    newEventStatus = state.newEventStatus,
                    selectedHour = state.selectedHour,
                    selectedMinute = state.selectedMinute,
                    onEvent = onEvent,
                )

                Spacer(modifier = Modifier.height(24.dp))

                MainButton(
                    modifier =
                        Modifier
                            .width(100.dp)
                            .height(30.dp)
                            .clip(RoundedCornerShape(13.dp))
                            .align(Alignment.CenterHorizontally),
                    text = stringResource(Res.string.add),
                    textStyle = MainTheme.typography.calendarDialog.addButtonText,
                    contentColor = MainTheme.colors.black,
                    backgroundColor = MainTheme.colors.button,
                    onClick = {
                        onEvent(CalendarEvents.OnSaveEvent)
                    },
                )
            }
        }
    }
}
