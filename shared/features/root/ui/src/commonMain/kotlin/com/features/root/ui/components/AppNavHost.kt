package com.features.root.ui.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import coil3.compose.setSingletonImageLoaderFactory
import com.features.base.domain.enum.Graph
import com.features.base.domain.enum.Screen
import com.features.splash.ui.SplashScreen
import com.features.ui.extension.BackHandler

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    startDestination: Screen = Screen.SPLASH,
) {
    setSingletonImageLoaderFactory { context ->
        newImageLoader(context = context, debug = true)
    }

    NavHost(
        navController = navHostController,
        startDestination = startDestination.name,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None }
    ) {
        composable(Screen.SPLASH.route) {
            SplashScreen()
        }

        navigation(
            route = Graph.AUTH.route,
            startDestination = Screen.WELCOME.route
        ) {
            composable(Screen.WELCOME.route) {
                BackHandler {}
                Text("WelcomeScreen")
            }
            composable(Screen.PHONE.route) {
                BackHandler {}
                Text("PhoneScreen")
            }
            composable(Screen.CONFIRM.route) {
                BackHandler {}
                Text("ConfirmScreen")
            }
        }

        composable(Screen.MAIN.route) {
            BackHandler {}
            Text("MainScreen")
        }
    }
}

fun NavHostController.handleBackNavigation() {
    val previousRoute = previousBackStackEntry?.destination?.route
    if (previousRoute != null) popBackStack()
    else resetStackAndNavigateTo(Screen.MAIN.name)
}

fun NavHostController.resetStackAndNavigateTo(route: String) = navigate(route) {
    popUpTo(0) {
        inclusive = true
    }
}

fun NavHostController.replaceScreen(oldRoute: String?, newRoute: String) = navigate(newRoute) {
    oldRoute?.let {
        popUpTo(it) {
            inclusive = true
        }
    }
}