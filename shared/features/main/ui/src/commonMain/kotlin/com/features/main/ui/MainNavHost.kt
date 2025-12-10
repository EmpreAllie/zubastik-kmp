package com.features.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.features.main.ui.com.features.main.ui.model.BottomNavItem
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainNavHost(
    mainNavController: NavHostController = rememberNavController()
) {
    val items = listOf(
        BottomNavItem.Calendar,
        BottomNavItem.Teeth,
        BottomNavItem.Telemedicine,
        BottomNavItem.Lectures,
        BottomNavItem.NeuralNetwork
    )

    Scaffold(
        bottomBar = {
            NavigationBar {

                val navBackStackEntry by mainNavController.currentBackStackEntryAsState()
                val currentRoute = navBackStackEntry?.destination?.route

                items.forEach { screen ->

                    CustomNavigationItem(
                        screen = screen,
                        currentRoute = currentRoute,
                        onClick = {
                            mainNavController.navigate(screen.route) {
                                popUpTo(mainNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                        }
                    )

                }
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = mainNavController,
            startDestination = {  },
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(BottomNavItem.Calendar.route) { Text("Календарь") }
            composable(BottomNavItem.Teeth.route) { Text("Зубы") }
            composable(BottomNavItem.Telemedicine.route) { Text("Телемедицина") }
            composable(BottomNavItem.NeuralNetwork.route) { Text("Нейросеть") }
            composable(BottomNavItem.Lectures.route) { Text("Лекции") }
        }
    }
}




@Composable
fun RowScope.CustomNavigationItem(
    screen: BottomNavItem,
    currentRoute: String?,
    onClick: () -> Unit
) {
    val isSelected = screen.route == currentRoute

    val background = if (isSelected) MainTheme.colors.selectedButton else MainTheme.colors.button

    Column(
        modifier = Modifier
            .weight(1f)
            .clickable(onClick = onClick),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Box(
            modifier = Modifier
                .size(56.dp)
                .padding(8.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(background)
        ) {
            Icon(
                painter = painterResource(screen.icon),
                contentDescription = screen.route,
                tint = MainTheme.colors.secondary
            )
        }
    }
}