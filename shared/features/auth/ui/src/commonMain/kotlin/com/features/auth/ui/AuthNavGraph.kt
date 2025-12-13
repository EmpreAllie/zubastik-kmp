package com.features.auth.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.features.auth.presentation.AuthViewModel
import com.features.base.domain.enum.Screen
import org.koin.compose.viewmodel.koinViewModel

//const val AUTH_GRAPH_ROUTE = "auth_graph"
/*
private enum class AuthScreen {
    WELCOME,        // экран с кнопками "Войти"
    PHONE_INPUT,    // экран с полем ввода номера телефона
    CODE_INPUT      // экран с вводом кода
}

 */


fun NavGraphBuilder.authGraph(
    navController: NavController,
    onNavigateToMain: () -> Unit
) {

    @Composable
    fun rememberViewModel(): AuthViewModel {
        // сохраняем ссылку на родительский навигационный граф...
        val parentEntry = remember(navController.currentBackStackEntry) {
            navController.getBackStackEntry(Screen.AUTH.name)
        }
        // ...чтобы сохранить единую ViewModel для всех трёх экранов
        val authViewModel: AuthViewModel = koinViewModel(viewModelStoreOwner = parentEntry)

        return authViewModel
    }


    composable("auth_welcome_route") {
        AuthWelcomeScreen(
            viewModel = rememberViewModel(),
            onNavigateToPhoneInput = {
                navController.navigate("auth_phone_route")
            },
            onNavigateToYandexLogin = {

            }
        )
    }

    composable("auth_phone_route") {
        AuthPhoneScreen(
            viewModel = rememberViewModel(),
            onNavigateToCodeInput = {
                navController.navigate("auth_code_route")
            }
        )
    }


    composable("auth_code_route") {
        AuthCodeScreen(
            viewModel = rememberViewModel(),
            onNavigateBack = {
                navController.popBackStack()
            }
        )
    }
}