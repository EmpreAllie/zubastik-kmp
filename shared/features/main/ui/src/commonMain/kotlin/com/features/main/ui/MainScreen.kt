package com.features.main.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.ripple.rememberRipple
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.features.main.ui.model.BottomNavItem
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.painterResource

@Composable
fun MainScreen(
    navController: NavHostController,
    content: @Composable () -> Unit,
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route

    val backgroundColor = MainTheme.colors.primary

    Scaffold(
        modifier =
            Modifier
                .background(backgroundColor),
        containerColor = backgroundColor,
        bottomBar = {
            CustomBottomBar(
                currentRoute = currentRoute,
                onItemClick = { item ->
                    if (currentRoute != item.route) {
                        navController.navigate(item.route) {
                            popUpTo(navController.graph.startDestinationRoute ?: "") {
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                },
            )
        },
    ) { paddingValues ->
        Box(
            modifier =
                Modifier
                    .fillMaxSize()
                    .background(backgroundColor)
                    .padding(paddingValues),
        ) {
            content()
        }
    }
}

@Composable
private fun CustomBottomBar(
    currentRoute: String?,
    onItemClick: (BottomNavItem) -> Unit,
) {
    Surface(
        modifier =
            Modifier
                .fillMaxWidth()
                .height(64.dp),
        color = MainTheme.colors.bottomNavBarBackground,
    ) {
        Row(
            modifier = Modifier.fillMaxSize(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            BottomNavItem.items.forEach { item ->
                val isSelected = currentRoute == item.route
                val painter = painterResource(item.icon)

                Box(
                    modifier =
                        Modifier
                            .weight(1f)
                            .fillMaxHeight()
                            .clickable(
                                interactionSource = remember { MutableInteractionSource() },
                                indication = null,
                            ) {
                                onItemClick(item)
                            },
                    contentAlignment = Alignment.Center,
                ) {
                    Icon(
                        painter = painter,
                        contentDescription = null,
                        modifier =
                            Modifier
                                .size(24.dp),
                        tint = if (isSelected) MainTheme.colors.secondary else MainTheme.colors.gray,
                    )
                }
            }
        }
    }
}
