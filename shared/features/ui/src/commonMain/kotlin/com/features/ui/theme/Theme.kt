package com.features.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import org.jetbrains.compose.resources.Font
import com.features.ui.Res
import com.features.ui.jura_bold
import com.features.ui.jura_regular

@Composable
fun MainTheme(
    isDarkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit,
) {
    val colors = if (isDarkTheme) Colors.Dark() else Colors.Light()

    CompositionLocalProvider(
        localColors provides colors,
        localTypography provides Typography(fontJura)
    ) {
        MaterialTheme(content = content)
    }
}

private val fontJura
    @Composable get() = FontFamily(
        Font(
            resource = Res.font.jura_regular,
            weight = FontWeight.Normal
        ),
        Font(
            resource = Res.font.jura_bold,
            weight = FontWeight.Bold
        )
    )

private val localColors = compositionLocalOf<Colors> { Colors.Light() }
private val localTypography = staticCompositionLocalOf<Typography> { error("No typography provided") }

object MainTheme {
    val colors: Colors @Composable get() = localColors.current
    val typography: Typography @Composable get() = localTypography.current
}