package com.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navigation
import com.features.auth.presentation.AuthViewModel
import org.koin.compose.viewmodel.koinViewModel


// Enum для определения каждого из экранов во флоу авторизации
private enum class AuthScreen {
    WELCOME,        // экран с кнопками "Войти"
    PHONE_INPUT,    // экран с полем ввода номера телефона
    CODE_INPUT      // экран с вводом кода
}

@Composable
fun AuthNavHost(
    authNavController: NavHostController
) {
    //val authNavController = rememberNavController()

    val viewModel: AuthViewModel = koinViewModel()

    NavHost(
        navController = authNavController,
        startDestination = AuthScreen.WELCOME.name
    ) {

        composable(AuthScreen.WELCOME.name) {

            AuthWelcomeScreen(
                viewModel = viewModel,
                onNavigateToPhoneInput = {
                    authNavController.navigate(AuthScreen.PHONE_INPUT.name)
                },
                onNavigateToYandexLogin = {

                }
            )

        }

        composable (AuthScreen.PHONE_INPUT.name) {
            AuthPhoneScreen(
                viewModel = viewModel,
                onNavigateToCodeInput = {
                    authNavController.navigate(AuthScreen.CODE_INPUT.name)
                }
            )
        }

        composable (AuthScreen.CODE_INPUT.name) {
            AuthCodeScreen(
                viewModel = viewModel,
                onNavigateBack = {
                    authNavController.popBackStack()
                }
            )
        }

    }
}
