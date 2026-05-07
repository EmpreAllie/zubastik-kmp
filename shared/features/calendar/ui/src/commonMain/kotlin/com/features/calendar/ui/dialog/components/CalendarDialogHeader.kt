package com.features.calendar.ui.dialog.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.features.calendar.presentation.model.CalendarEvents
import com.features.calendar.ui.utils.toFullStringResource
import com.features.calendar.ui.utils.toGenitiveFormat
import com.features.calendar.ui.utils.toGenitiveStringResource
import com.features.ui.Res
import com.features.ui.ic_cross
import com.features.ui.theme.MainTheme
import kotlinx.datetime.LocalDate
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarDialogHeader(
    date: LocalDate,
    onDismiss: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.Top,
    ) {
        Column {
            Text(
                text = date.toGenitiveFormat(),
                style = MainTheme.typography.calendarDialog.selectedDay,
                color = MainTheme.colors.black,
            )

            Text(
                text = stringResource(date.dayOfWeek.toFullStringResource()),
                style = MainTheme.typography.calendarDialog.selectedDay,
                color = MainTheme.colors.black,
            )
        }

        IconButton(
            onClick = onDismiss,
            modifier = Modifier.size(24.dp),
        ) {
            Icon(
                painter = painterResource(Res.drawable.ic_cross),
                contentDescription = null,
                tint = MainTheme.colors.black,
            )
        }
    }
}
