package com.features.splash.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.scale
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import coil3.compose.LocalPlatformContext
import com.core.data.utils.globalApplicationContext
import com.core.data.utils.localize
import com.features.splash.domain.SplashRepository
import com.features.splash.presentation.SplashViewModel
import com.features.splash.presentation.model.SplashNavigationEvent
import com.features.ui.Res
import com.features.ui.ic_logo_zubastik
import com.features.ui.theme.MainTheme
import com.resources.MultiplatformResource
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
internal fun SplashScreenContent(
    viewModel: SplashViewModel,
    navigateToAuth: () -> Unit,
    navigateToMain: () -> Unit
) {
    // для запуска в первый раз, т.к. Unit никогда не изменится,
    // и LaunchedEffect вызовет функцию loadAndNavigate() только 1 раз
    LaunchedEffect(Unit) {
        viewModel.loadAndNavigate()
    }

    // для запуска и ожидания остальных событий
    LaunchedEffect(viewModel.navigationEvent) {
        viewModel.navigationEvent.collect { event ->
            when(event) {
                SplashNavigationEvent.NavigateToAuth -> navigateToAuth()
                SplashNavigationEvent.NavigateToMain -> navigateToMain()
            }
        }
    }

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
                painter = painterResource(Res.drawable.ic_logo_zubastik),
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

// реализация интерфейса, где не реализована функция isAuthenticated()
class DummySplashRepo : SplashRepository {
    override suspend fun isAuthenticated(): Boolean = false
}


@Composable
@Preview
fun SplashScreenContent_Preview() {
    globalApplicationContext = LocalPlatformContext.current

    val dummyRepo = remember { DummySplashRepo() }
    val dummyVM = remember { SplashViewModel(dummyRepo) }

    MainTheme {
        SplashScreenContent(
            viewModel = dummyVM,
            navigateToAuth = {},
            navigateToMain = {}
        )
    }
}