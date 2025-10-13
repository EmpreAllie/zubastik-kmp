package com.features.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.features.base.domain.enum.Screen
import com.features.root.ui.components.AppNavHost
import com.features.root.ui.components.handleBackNavigation
import com.features.root.ui.components.resetStackAndNavigateTo
import com.features.ui.extension.BackHandler
import com.features.ui.extension.LockScreenOrientation
import com.features.ui.theme.MainTheme
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEvents

@Composable
fun RootApp(
    viewModel: RootViewModel,
    navHostController: NavHostController = rememberNavController(),
) {
    val basicNavOption = navOptions {
        popUpTo(Screen.SPLASH.name) {
            inclusive = true
        }
    }

    val state by viewModel.state.collectAsState()

    LockScreenOrientation()


    // главный UI-контейнер, который вызывает AppNavHost - навигатор между экранами
    MainTheme {
        AppNavHost(navHostController) // тут решается, какой экран будет отображён

        BackHandler { navHostController.handleBackNavigation() }
    }


    state.screen?.let {
        val isClearStack = state.isClearStack

        with(navHostController) {
            if (isClearStack) resetStackAndNavigateTo(it.name)
            else navigate(it.name, basicNavOption)
        }
    }

    if (state.isPopScreen) {
        navHostController.handleBackNavigation()
        viewModel.onEvent(RootEvents.ClearIsPop)
    }
}