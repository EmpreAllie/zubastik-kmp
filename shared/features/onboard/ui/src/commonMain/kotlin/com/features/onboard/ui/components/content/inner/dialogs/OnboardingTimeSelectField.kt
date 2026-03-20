package com.features.onboard.ui.components.content.inner.dialogs

import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.features.ui.Res
import com.features.ui.ic_clock
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun OnboardingTimeSelectField(
    time: String,
    onTimeChange: (String) -> Unit
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .border(1.dp, MainTheme.colors.black.copy(alpha = 0.35f), RoundedCornerShape(80.dp))
            .clickable {}
            .padding(vertical = 8.dp, horizontal = 12.dp),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Text(
            text = time,
            color = MainTheme.colors.secondary,
            style = MainTheme.typography.onboardingBrushingDialog.brushingFrequencyListItem
        )

        Icon(
            painter = painterResource(Res.drawable.ic_clock),
            contentDescription = "Clock icon",
            tint = MainTheme.colors.secondary
        )
    }
}