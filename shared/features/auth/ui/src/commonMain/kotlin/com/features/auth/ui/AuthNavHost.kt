package com.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import com.features.auth.presentation.AuthViewModel
import com.features.auth.presentation.model.AuthEffects
import org.koin.compose.viewmodel.koinViewModel


// Enum для определения каждого из экранов во флоу авторизации
private enum class AuthScreen {
    WELCOME,        // экран с кнопками "Войти"
    PHONE_INPUT,    // экран с полем ввода номера телефона
    CODE_INPUT      // экран с вводом кода
}

@Composable
fun AuthNavHost(
    authNavController: NavHostController,
    onNavigateToMain: () -> Unit
) {
    val viewModel: AuthViewModel = koinViewModel()

    LaunchedEffect(viewModel) {
        viewModel.effect.collect { effect ->
            when (effect) {
                is AuthEffects.NavigateToMain -> {
                    onNavigateToMain()
                }
            }
        }
    }

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
