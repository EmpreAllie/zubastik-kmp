package com.features.main.ui.com.features.main.ui.model

import com.features.ui.ic_calendar
import org.jetbrains.compose.resources.DrawableResource
import com.features.ui.Res
import com.features.ui.ic_lectures
import com.features.ui.ic_neural_network
import com.features.ui.ic_telemedicine
import com.features.ui.ic_tooth

sealed class BottomNavItem(
    val route: String,
    val icon: DrawableResource
) {
    data object Calendar: BottomNavItem(
        route = "calendar",
        icon = Res.drawable.ic_calendar
    )

    data object Teeth: BottomNavItem(
        route = "teeth",
        icon = Res.drawable.ic_tooth
    )

    data object Telemedicine: BottomNavItem(
        route = "telemedicine",
        icon = Res.drawable.ic_telemedicine
    )

    data object Lectures: BottomNavItem(
        route = "lectures",
        icon = Res.drawable.ic_lectures
    )

    data object NeuralNetwork: BottomNavItem(
        route = "neural_network",
        icon = Res.drawable.ic_neural_network
    )

}