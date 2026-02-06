package com.features.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.ui.Res
import com.features.ui.appName
import com.features.ui.ic_logo
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun AppLogo(modifier: Modifier = Modifier) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            modifier = Modifier
                .size(250.dp),
            painter = painterResource(Res.drawable.ic_logo),
            contentDescription = "App Logo",
        )

        Text(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(Res.string.appName),
            style = MainTheme.typography.main.title,
            color = MainTheme.colors.secondary,
            textAlign = TextAlign.Center,
        )
    }
}