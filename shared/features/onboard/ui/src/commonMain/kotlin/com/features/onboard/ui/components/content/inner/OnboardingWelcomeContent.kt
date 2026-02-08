package com.features.onboard.ui.components.content.inner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import org.jetbrains.compose.resources.painterResource
import com.features.ui.Res
import com.features.ui.onboardingInfo
import com.features.ui.theme.MainTheme
import com.features.ui.zub
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingWelcomeContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            painterResource(Res.drawable.zub),
            contentDescription = "ZUB!!!!"
        )

        Text(
            text = stringResource(Res.string.onboardingInfo),
            style = MainTheme.typography.onboarding.chatContainerContent
        )
    }
}