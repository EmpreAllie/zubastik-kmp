package com.features.ui.components

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.features.ui.Res
import com.features.ui.or
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun OrDivider(modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(horizontal = 24.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(24.dp)
    ) {
        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MainTheme.colors.black.copy(alpha = 0.3f)
        )

        Text(
            text = stringResource(Res.string.or),
            style = MainTheme.typography.main.main,
            color = MainTheme.colors.black.copy(alpha = 0.3f)
        )

        HorizontalDivider(
            modifier = Modifier.weight(1f),
            color = MainTheme.colors.black.copy(alpha = 0.3f)
        )
    }
}
