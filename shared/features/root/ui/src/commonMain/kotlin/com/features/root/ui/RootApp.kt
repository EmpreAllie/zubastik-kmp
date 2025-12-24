package com.features.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.features.base.domain.enum.Screen
import com.features.root.ui.components.AppNavHost
import com.features.ui.extension.LockScreenOrientation
import com.features.ui.theme.MainTheme
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEffect
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun RootApp(
    viewModel: RootViewModel = koinViewModel(),
    navHostController: NavHostController = rememberNavController(),
) {
    LaunchedEffect(Unit) {
        val basicNavOption = navOptions {
            popUpTo(Screen.SPLASH.route) {
                inclusive = false
            }
        }

        viewModel.effect.collect { effect ->
            when (effect) {
                RootEffect.PopBackStack -> navHostController.handleBackNavigation(basicNavOption)

                is RootEffect.NavigateWithClearStack ->
                    navHostController.resetStackAndNavigateTo(effect.destination.route, basicNavOption)

                is RootEffect.Navigate ->  {
                    navHostController.navigate(effect.destination.route, basicNavOption)
                }

                is RootEffect.ReplaceScreen -> {
                    val curRoute = navHostController.currentBackStackEntry?.destination?.route
                    navHostController.replaceScreen(
                        oldRoute = curRoute,
                        newRoute = effect.destination.route
                    )
                }
            }
        }
    }

    LockScreenOrientation()

    MainTheme {
        AppNavHost(navHostController = navHostController)
    }
}

private fun NavHostController.handleBackNavigation(basicNavOptions: NavOptions) {
    val previousRoute = previousBackStackEntry?.destination?.route
    if (previousRoute != null) popBackStack()
    else resetStackAndNavigateTo(Screen.MAIN.route, basicNavOptions)
}

private fun NavHostController.resetStackAndNavigateTo(route: String, basicNavOptions: NavOptions) {
    navigate(route, basicNavOptions)
}

private fun NavHostController.replaceScreen(oldRoute: String?, newRoute: String) =
    navigate(newRoute) {
        oldRoute?.let {
            popUpTo(it) {
                inclusive = true
            }
        }
    }
