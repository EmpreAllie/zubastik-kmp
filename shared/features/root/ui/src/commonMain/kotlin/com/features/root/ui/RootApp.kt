package com.features.root.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.NavHostController
import androidx.navigation.NavOptions
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navOptions
import com.features.base.AppEvent
import com.features.base.AuthEventBus
import com.features.base.domain.enum.Graph
import com.features.base.domain.enum.Screen
import com.features.root.ui.components.AppNavHost
import com.features.ui.extension.BackHandler
import com.features.ui.extension.LockScreenOrientation
import com.features.ui.theme.MainTheme
import com.root.presentation.RootViewModel
import com.root.presentation.model.RootEffect
import com.root.presentation.model.RootEvent
import org.koin.compose.viewmodel.koinViewModel


@Composable
fun RootApp(
    viewModel: RootViewModel = koinViewModel(),
    navHostController: NavHostController = rememberNavController(),
) {
    val state by viewModel.state.collectAsState()

    LaunchedEffect(state.destination) {
        val newDestination = state.destination
        if (newDestination != null && navHostController.currentDestination?.route != newDestination.route) {

            navHostController.navigate(newDestination.route) {
                // очистка стека
            }
        }
    }

    BackHandler(enabled = state.screenStack.size > 1) {
        viewModel.onEvent(RootEvent.OnClickBack)
    }

    // дргуие эффекты
    LaunchedEffect(Unit) {
        AuthEventBus.event.collect { event ->
            when(event) {
                AppEvent.Logout -> {
                    viewModel.onEvent(
                        RootEvent.OnSetScreen(destination = Graph.AUTH, isClearStack = true)
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