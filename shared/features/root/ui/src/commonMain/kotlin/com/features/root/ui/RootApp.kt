package com.features.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.features.base.domain.enum.Screen
import com.features.root.ui.components.AppNavHost
import com.features.root.ui.components.handleBackNavigation
import com.features.root.ui.components.resetStackAndNavigateTo
import com.features.ui.extension.BackHandler
import com.features.ui.extension.LockScreenOrientation
import com.features.ui.theme.MainTheme
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEffect
import com.root.presentation.model.RootEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RootApp(
    viewModel: RootViewModel = koinViewModel(),
    navHostController: NavHostController = rememberNavController(),
) {
    LockScreenOrientation()

    MainTheme {
        AppNavHost(
            navHostController = navHostController,
            rootViewModel = viewModel
        )
        BackHandler { viewModel.onEvent(RootEvent.OnClickBack) }
    }

    LaunchedEffect(Unit) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is RootEffect.Navigate -> {
                    navHostController.navigate(effect.route) {
                        popUpTo(Screen.SPLASH.name) {
                            inclusive = true
                        }
                    }
                }

                is RootEffect.NavigateWithClearStack -> {
                    navHostController.resetStackAndNavigateTo(effect.route)
                }

                is RootEffect.ReplaceScreen -> {
                    navHostController.popBackStack()
                    navHostController.navigate(effect.route) {
                        popUpTo(Screen.SPLASH.name) {
                            inclusive = true
                        }
                    }
                }

                RootEffect.PopBackStack -> {
                    navHostController.handleBackNavigation()
                }
            }
        }
    }
}
