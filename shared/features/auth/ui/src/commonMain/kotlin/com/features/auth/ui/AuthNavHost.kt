package com.features.auth.ui

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController


// Enum для определения каждого из экранов во флоу авторизации
private enum class AuthScreen {
    WELCOME,        // экран с кнопками "Войти"
    PHONE_INPUT,    // экран с полем ввода номера телефона
    CODE_INPUT      // экран с вводом кода
}

@Composable
fun AuthNavHost() {
    val authNavController = rememberNavController()

    NavHost(
        navController = authNavController,
        startDestination = AuthScreen.WELCOME.name
    ) {
        composable(AuthScreen.WELCOME.name) {
            AuthWelcomeScreen(
                onNavigateToPhoneInput = {
                    authNavController.navigate(AuthScreen.PHONE_INPUT.name)
                }
            )
        }

        composable (AuthScreen.PHONE_INPUT.name) {
            Text("Phone Input Screen")
        }

        composable (AuthScreen.CODE_INPUT.name) {

        }
    }
}
