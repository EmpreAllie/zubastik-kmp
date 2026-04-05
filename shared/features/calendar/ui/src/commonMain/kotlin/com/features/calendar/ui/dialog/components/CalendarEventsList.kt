package com.features.calendar.ui.dialog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
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
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.features.calendar.domain.model.CalendarEvent
import com.features.calendar.ui.utils.getEventColor
import com.features.ui.Res
import com.features.ui.events
import com.features.ui.noDescription
import com.features.ui.noEventsYet
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

// Список событий при нажатии на дату
@Composable
fun CalendarEventsList(
    events: List<CalendarEvent>,
    onDeleteEvent: (CalendarEvent) -> Unit,
) {
    if (events.isEmpty()) {
        // "Пока нет событий"
        Text(
            text = stringResource(Res.string.noEventsYet),
            modifier = Modifier.padding(vertical = 40.dp),
            style = MainTheme.typography.calendarDialog.defaultEventText,
            color = MainTheme.colors.black,
        )
    } else {
        // "События"
        Text(
            text = stringResource(Res.string.events),
            style = MainTheme.typography.calendarDialog.eventsHeader,
            color = MainTheme.colors.black,
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Элементы списка
        events.forEach {
            EventItem(
                event = it,
                onDeleteEvent = onDeleteEvent,
            )
        }
    }
}

// Одно событие в списке
@Composable
fun EventItem(
    event: CalendarEvent,
    onDeleteEvent: (CalendarEvent) -> Unit,
) {
    Column(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp),
    ) {
        // время события
        Text(
            text = event.time,
            style = MainTheme.typography.calendarDialog.defaultEventText,
            color = MainTheme.colors.eventTimeGray,
        )

        Row(
            modifier =
                Modifier
                    .fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween,
        ) {
            // Описание события
            Text(
                text = event.description.ifBlank { stringResource(Res.string.noDescription) },
                modifier = Modifier.weight(1f),
                style = MainTheme.typography.calendarDialog.defaultEventText,
                color = MainTheme.colors.black,
            )

            Row(
                verticalAlignment = Alignment.CenterVertically,
            ) {
                // Кнопка "минус"
                Box(
                    modifier =
                        Modifier
                            .clip(RoundedCornerShape(4.dp))
                            .clickable(
                                onClick = { onDeleteEvent(event) },
                            ).padding(6.dp),
                ) {
                    Box(
                        modifier =
                            Modifier
                                .width(12.dp)
                                .height(2.dp)
                                .background(MainTheme.colors.black),
                    )
                }

                Spacer(modifier = Modifier.width(8.dp))

                // Цветной квадратик со статусом
                Box(
                    modifier =
                        Modifier
                            .size(24.dp)
                            .clip(RoundedCornerShape(10.dp))
                            .background(getEventColor(event.status)),
                )
            }
        }
    }
}
