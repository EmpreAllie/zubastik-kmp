package com.features.auth.ui

import androidx.compose.runtime.Composable
import com.features.auth.ui.components.AuthWelcomeScreenContent


@Composable
fun AuthWelcomeScreen(
    onNavigateToPhoneInput: () -> Unit
) {
    AuthWelcomeScreenContent(
        onLoginClick = {
            onNavigateToPhoneInput()
        }
    )
}
