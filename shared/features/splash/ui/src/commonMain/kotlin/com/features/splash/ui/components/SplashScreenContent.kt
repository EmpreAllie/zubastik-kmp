package com.features.splash.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import com.core.data.utils.globalApplicationContext
import com.core.data.utils.localize
import com.features.splash.presentation.model.SplashEvents
import com.features.splash.presentation.model.SplashState
import com.features.ui.Res
import com.features.ui.ic_logo
import com.features.ui.theme.MainTheme
import com.resources.MultiplatformResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SplashScreenContent(
    state: SplashState,
    onEvent: (SplashEvents) -> Unit
) {

    Scaffold (
        containerColor = MainTheme.colors.primary
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
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
                text = MultiplatformResource.strings.appName.localize(),
                style = MainTheme.typography.main.title,
                color = MainTheme.colors.secondary,
                textAlign = TextAlign.Center,
            )
        }
    }
}

@Composable
@Preview
fun SplashScreenContent_Preview() {
    globalApplicationContext = LocalPlatformContext.current

    MainTheme {
        /*
        SplashScreenContent(
            viewModel = dummyVM,
            navigateToAuth = {},
            navigateToMain = {}
        )
         */
    }
}