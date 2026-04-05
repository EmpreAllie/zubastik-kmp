package com.features.calendar.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.calendar.ui.utils.toStringResource
import com.features.ui.Res
import com.features.ui.april
import com.features.ui.august
import com.features.ui.december
import com.features.ui.february
import com.features.ui.ic_arrow_next
import com.features.ui.ic_arrow_prev
import com.features.ui.january
import com.features.ui.july
import com.features.ui.june
import com.features.ui.march
import com.features.ui.may
import com.features.ui.november
import com.features.ui.october
import com.features.ui.september
import com.features.ui.theme.MainTheme
import kotlinx.datetime.LocalDate
import kotlinx.datetime.Month
import org.jetbrains.compose.resources.StringResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarMonthSelector(
    selectedMonthDate: LocalDate,
    onPrevClick: () -> Unit,
    onNextClick: () -> Unit,
) {
    Row(
        modifier =
            Modifier
                .fillMaxWidth(),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Spacer(modifier = Modifier.weight(1f))

        Text(
            text = stringResource(selectedMonthDate.month.toStringResource()) + " " + selectedMonthDate.year.toString(),
            style = MainTheme.typography.calendar.monthTitle,
            color = MainTheme.colors.black,
            textAlign = TextAlign.Center,
        )

        Row(
            modifier = Modifier.weight(1f),
            horizontalArrangement = Arrangement.End,
            verticalAlignment = Alignment.CenterVertically,
        ) {
            IconButton(
                onClick = onPrevClick,
                modifier = Modifier.size(30.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_prev),
                    contentDescription = null,
                    tint = MainTheme.colors.black,
                )
            }

            IconButton(
                onClick = onNextClick,
                modifier = Modifier.size(30.dp),
            ) {
                Icon(
                    painter = painterResource(Res.drawable.ic_arrow_next),
                    contentDescription = null,
                    tint = MainTheme.colors.black,
                )
            }
        }
    }
}
