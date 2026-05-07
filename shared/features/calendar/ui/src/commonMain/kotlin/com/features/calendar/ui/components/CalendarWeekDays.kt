package com.features.calendar.ui.components

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import com.features.ui.Res
import com.features.ui.fridayShort
import com.features.ui.mondayShort
import com.features.ui.saturdayShort
import com.features.ui.sundayShort
import com.features.ui.theme.MainTheme
import com.features.ui.thursdayShort
import com.features.ui.tuesdayShort
import com.features.ui.wednesdayShort
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarWeekDays() {
    val days =
        listOf(
            Res.string.mondayShort,
            Res.string.tuesdayShort,
            Res.string.wednesdayShort,
            Res.string.thursdayShort,
            Res.string.fridayShort,
            Res.string.saturdayShort,
            Res.string.sundayShort,
        )

    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
    ) {
        days.forEach {
            Text(
                text = stringResource(it),
                modifier = Modifier.weight(1f),
                style = MainTheme.typography.calendar.gridDayItem,
                color = MainTheme.colors.weekDaysGray,
                textAlign = TextAlign.Center,
            )
        }
    }
}
