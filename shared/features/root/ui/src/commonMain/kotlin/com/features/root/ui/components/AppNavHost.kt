package com.features.root.ui.components

//import com.features.main.ui.MainNavHost
import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import coil3.compose.setSingletonImageLoaderFactory
import com.features.auth.ui.authGraph
import com.features.base.domain.enum.Screen
import com.features.splash.ui.SplashScreen

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

        composable(Screen.SPLASH.name) {
            SplashScreen()
        }

        navigation(
            startDestination = "auth_welcome_route",
            route = Screen.AUTH.name
        ) {
            authGraph(
                navController = navHostController,
                onNavigateToMain = {
                    navHostController.resetStackAndNavigateTo(Screen.MAIN.name)
                }
            )
        }

        composable(Screen.MAIN.name) {

        }
        /*
        // Навигационные графы
        Screen.entries.forEach { screen ->
            composable(screen.name) {
                when (screen) {
                    Screen.SPLASH -> SplashScreen()
                    /*
                    Screen.AUTH -> AuthNavHost(
                        authNavController = authNavController,
                        onNavigateToMain = {
                            navHostController.resetStackAndNavigateTo(Screen.MAIN.name)
                        }
                    )

                     */
                    Screen.MAIN -> {}
                }
            }
        }

         */
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