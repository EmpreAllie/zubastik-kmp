package com.features.calendar.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.features.ui.Res
import com.features.ui.calendar
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun CalendarHeader() {
    Text(
        text = stringResource(Res.string.calendar),
        style = MainTheme.typography.calendar.title,
        color = MainTheme.colors.secondary,
    )

    Spacer(modifier = Modifier.height(8.dp))

    Box(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(1.dp)
                .background(MainTheme.colors.secondary)
                .padding(50.dp),
    )

    Spacer(modifier = Modifier.height(20.dp))
}
