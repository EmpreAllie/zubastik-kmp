package com.features.calendar.ui.dialog.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.calendar.domain.model.CalendarEventStatus
import com.features.calendar.presentation.model.CalendarEvents
import com.features.calendar.ui.utils.getEventColor
import com.features.calendar.ui.utils.toStringResource
import com.features.ui.Res
import com.features.ui.components.WheelTimePicker
import com.features.ui.enterDescription
import com.features.ui.events
import com.features.ui.newEvent
import com.features.ui.textInput.NotesTextField
import com.features.ui.theme.MainTheme
import com.features.ui.timeOfEvent
import com.features.ui.typeOfEvent
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarEventCreationView(
    eventDescription: String,
    newEventStatus: CalendarEventStatus,
    selectedHour: Int,
    selectedMinute: Int,
    onEvent: (CalendarEvents) -> Unit,
) {
    // "Новое событие"
    Text(
        text = stringResource(Res.string.newEvent),
        modifier = Modifier.padding(top = 20.dp, bottom = 15.dp),
        style = MainTheme.typography.calendarDialog.eventsHeader,
        color = MainTheme.colors.black,
    )

    NotesTextField(
        text = eventDescription,
        onTextChange = { newText ->
            onEvent(CalendarEvents.OnTextChanged(newText))
        },
        hintText = stringResource(Res.string.enterDescription),
        textAndFocusedBorderColor = MainTheme.colors.black,
    )

    Spacer(modifier = Modifier.height(16.dp))

    // "Тип события"
    Text(
        text = stringResource(Res.string.typeOfEvent),
        modifier = Modifier.fillMaxWidth(),
        style = MainTheme.typography.calendarDialog.eventTypeHeader,
        color = MainTheme.colors.black,
        textAlign = TextAlign.Start,
    )

    // ряд с цветными квадратиками
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        CalendarEventStatus.entries.forEach { statusFromEnum ->
            StatusColoredBox(
                status = statusFromEnum,
                isSelected = statusFromEnum == newEventStatus,
                onClick = { newStatus ->
                    onEvent(CalendarEvents.OnStatusSelected(newStatus))
                },
            )
        }
    }

    // Наименование типа события
    Text(
        text = stringResource(newEventStatus.toStringResource()),
        modifier = Modifier.fillMaxWidth(),
        style = MainTheme.typography.calendarDialog.eventType,
        color = MainTheme.colors.gray,
        textAlign = TextAlign.Start,
    )

    Spacer(modifier = Modifier.height(20.dp))

    // "Время события"
    Text(
        text = stringResource(Res.string.timeOfEvent),
        style = MainTheme.typography.calendarDialog.eventTypeHeader,
        color = MainTheme.colors.black,
        textAlign = TextAlign.Center,
    )

    Spacer(modifier = Modifier.height(16.dp))

    // Выбор времени
    Row(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(80.dp),
        horizontalArrangement = Arrangement.Center,
        verticalAlignment = Alignment.CenterVertically,
    ) {
        WheelTimePicker(
            modifier = Modifier.width(60.dp),
            items = (0..23).map { it.toString().padStart(2, '0') },
            initialIndex = selectedHour,
            onItemSelected = { newHour ->
                onEvent(CalendarEvents.OnEventHourChanged(hour = newHour))
            },
        )

        Text(
            text = ":",
            modifier = Modifier.padding(horizontal = 10.dp),
            style = MainTheme.typography.calendarDialog.timeSeparator,
            color = MainTheme.colors.black,
        )

        WheelTimePicker(
            modifier = Modifier.width(60.dp),
            items = (0..59).map { it.toString().padStart(2, '0') },
            initialIndex = selectedMinute,
            onItemSelected = { newMinute ->
                onEvent(CalendarEvents.OnEventMinuteChanged(minute = newMinute))
            },
        )
    }
}

@Composable
fun StatusColoredBox(
    status: CalendarEventStatus,
    isSelected: Boolean,
    onClick: (CalendarEventStatus) -> Unit,
) {
    Box(
        modifier =
            Modifier
                .size(35.dp)
                .clip(RoundedCornerShape(10.dp))
                .background(getEventColor(status))
                .border(
                    width = if (isSelected) 2.dp else 0.dp,
                    color = if (isSelected) MainTheme.colors.black else Color.Transparent,
                    shape = RoundedCornerShape(10.dp),
                ).clickable { onClick(status) },
    )
}

@Composable
private fun TimeBox(text: String) {
    Box(
        modifier =
            Modifier
                .clip(RoundedCornerShape(5.dp))
                .background(MainTheme.colors.timeSelectorBoxGray)
                .padding(horizontal = 12.dp, vertical = 8.dp),
    ) {
        Text(
            text = text,
            style = MainTheme.typography.calendarDialog.timeBoxContent,
            color = MainTheme.colors.black,
        )
    }
}
