package com.features.calendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.calendar.domain.model.CalendarDay
import com.features.calendar.domain.model.CalendarEventStatus
import com.features.calendar.ui.utils.getEventColor
import com.features.ui.theme.MainTheme
import kotlinx.datetime.LocalDate

@Composable
fun CalendarDateGrid(
    days: List<CalendarDay>,
    onDayClick: (LocalDate) -> Unit,
) {
    LazyVerticalGrid(
        columns = GridCells.Fixed(7),
        modifier =
            Modifier
                .fillMaxWidth(),
        userScrollEnabled = false,
        verticalArrangement = Arrangement.spacedBy(8.dp),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
    ) {
        items(days) {
            DayItem(
                day = it,
                onClick = onDayClick,
            )
        }
    }
}

@Composable
fun DayItem(
    day: CalendarDay,
    onClick: (LocalDate) -> Unit,
) {
    val date = day.date

    val textColor = if (day.statuses.isNotEmpty()) MainTheme.colors.containerBackground else MainTheme.colors.black

    Box(
        modifier =
            Modifier
                .aspectRatio(1f)
                .clip(RoundedCornerShape(10.dp))
                .background(MainTheme.colors.transparent)
                .then(
                    if (date != null) Modifier.clickable { onClick(date) } else Modifier,
                ),
        contentAlignment = Alignment.Center,
    ) {
        if (day.statuses.isNotEmpty()) {
            Column(
                modifier = Modifier.matchParentSize(),
            ) {
                day.statuses.forEach { status ->
                    Box(
                        modifier =
                            Modifier
                                .weight(1f)
                                .fillMaxWidth()
                                .background(getEventColor(status)),
                    )
                }
            }
        }

        if (date != null) {
            Text(
                text = date.day.toString(),
                style = MainTheme.typography.calendar.gridDayItem,
                color = textColor,
                textAlign = TextAlign.Center,
            )
        }
    }
}
