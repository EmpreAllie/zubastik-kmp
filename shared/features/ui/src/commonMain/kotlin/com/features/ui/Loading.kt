package com.features.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.unit.dp
import com.features.ui.extension.noRippleClickable
import com.features.ui.theme.MainTheme

@Composable
fun LoadingContent(
    modifier: Modifier = Modifier,
    color: Color = MainTheme.colors.primary
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .noRippleClickable({})
            .background(MainTheme.colors.black.copy(alpha = 0.6f)),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = modifier.size(22.dp),
            color = color,
            trackColor = MainTheme.colors.transparent,
            strokeWidth = 2.dp,
            strokeCap = StrokeCap.Square
        )
    }
}