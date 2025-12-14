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
import com.features.auth.ui.AuthCodeScreen
import com.features.auth.ui.AuthPhoneScreen
import com.features.auth.ui.AuthWelcomeScreen
import com.features.base.domain.enum.Graph
import com.features.base.domain.enum.Screen
import com.features.splash.ui.SplashScreen
import com.features.ui.extension.BackHandler
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    rootViewModel: RootViewModel,
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
        composable(Screen.MAIN.route) {
            BackHandler {}
            Text("MainScreen")
        }

        Graph.entries.forEach { graph ->
            navigation(route = graph.route, startDestination = graph.screens.first().name) {
                graph.screens.forEach { screen ->
                    composable(screen.route) {

                        when(screen) {
                            Screen.WELCOME -> AuthWelcomeScreen() // Просто вызываем экран
                            Screen.PHONE -> AuthPhoneScreen()       // Просто вызываем экран
                            Screen.CONFIRM -> AuthCodeScreen()      // Просто вызываем экран
                            else -> {
                                BackHandler {}
                                Text("${screen.route}Screen")
                            }
                            /*
                            Screen.WELCOME -> AuthWelcomeScreen(
                                onNavigateToPhoneInput = {
                                    //navHostController.navigate(Screen.PHONE.route)
                                    rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.PHONE))
                                },
                                onNavigateToYandexLogin = {

                                }
                            )

                            Screen.PHONE -> AuthPhoneScreen(
                                onNavigateToCodeInput = {
                                    //navHostController.navigate(Screen.CONFIRM.route)
                                    rootViewModel.onEvent(RootEvent.OnSetScreen(Screen.PHONE))
                                }
                            )

                            Screen.CONFIRM -> AuthCodeScreen(
                                onNavigateBack = {
                                    //navHostController.popBackStack()
                                    rootViewModel.onEvent(RootEvent.OnClickBack)
                                }
                            )

                            else -> {
                                BackHandler {}
                                Text("${screen.route}Screen")
                            }

                             */
                        }
                    }
                }
            }
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