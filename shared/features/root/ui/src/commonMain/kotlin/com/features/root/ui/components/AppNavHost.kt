package com.features.root.ui.components

import androidx.compose.animation.EnterTransition
import androidx.compose.animation.ExitTransition
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
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
import com.features.calendar.ui.CalendarScreen
import com.features.main.ui.MainScreen
import com.features.main.ui.model.BottomNavItem
import com.features.onboard.ui.OnboardingScreen
import com.features.splash.ui.SplashScreen
import com.features.teeth.ui.TeethScreen
import com.features.ui.extension.BackHandler
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvent
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun AppNavHost(
    navHostController: NavHostController,
    viewModel: RootViewModel = koinViewModel(),
    startDestination: Screen = Screen.MAIN,
) {
    setSingletonImageLoaderFactory { context ->
        newImageLoader(context = context, debug = true)
    }

    NavHost(
        navController = navHostController,
        startDestination = startDestination.route,
        enterTransition = { EnterTransition.None },
        exitTransition = { ExitTransition.None },
    ) {
        composable(Screen.SPLASH.route) {
            SplashScreen()
        }

        Graph.entries.forEach { graph ->
            navigation(
                route = graph.route,
                startDestination = graph.screens.first().route,
            ) {
                graph.screens.forEach { screen ->
                    composable(screen.route) {
                        when (screen) {
                            Screen.WELCOME -> {
                                AuthWelcomeScreen()
                                BackHandler {}
                            }

                            Screen.PHONE -> {
                                AuthPhoneScreen()
                                BackHandler { viewModel.onEvent(RootEvent.OnClickBack) }
                            }

                            Screen.CODE -> {
                                AuthCodeScreen()
                                BackHandler { viewModel.onEvent(RootEvent.OnClickBack) }
                            }

                            else -> {
                                Text("${screen.route}Screen")
                                BackHandler {}
                            }
                        }
                    }
                }
            }
        }

        composable(Screen.ONBOARDING.route) {
            OnboardingScreen()
            // BackHandler {}
        }

        composable(Screen.MAIN.route) {
            LaunchedEffect(Unit) {
                navHostController.navigate(BottomNavItem.Calendar.route) {
                    popUpTo(Screen.MAIN.route) { inclusive = true }
                }
            }
        }

        composable(BottomNavItem.Calendar.route) {
            MainScreen(navHostController) {
                CalendarScreen()
                // BackHandler {}
            }
        }

        composable(BottomNavItem.Teeth.route) {
            MainScreen(navHostController) {
                TeethScreen()
                // BackHandler {}
            }
        }

        composable(BottomNavItem.Telemedicine.route) {
            MainScreen(navHostController) {
                Text("Telemedicine Screen")
                // CalendarScreen()
                BackHandler {}
            }
        }

        composable(BottomNavItem.NeuralNetwork.route) {
            MainScreen(navHostController) {
                Text("AI Screen")
                // CalendarScreen()
                BackHandler {}
            }
        }

        composable(BottomNavItem.Lectures.route) {
            MainScreen(navHostController) {
                Text("Lectures Screen")
                // CalendarScreen()
                BackHandler {}
            }
        }
    }
}
