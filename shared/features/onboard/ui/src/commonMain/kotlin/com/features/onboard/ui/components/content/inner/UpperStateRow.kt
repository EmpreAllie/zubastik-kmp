package com.features.onboard.ui.components.content.inner

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.features.onboard.presentation.model.OnboardingScreenState
import com.features.onboard.presentation.model.OnboardingState
import com.features.ui.Res
import com.features.ui.chatWithZub
import com.features.ui.teethType
import com.features.ui.theme.MainTheme
import com.features.ui.welcome
import org.jetbrains.compose.resources.stringResource

@Composable
fun UpperStateRow(
    state: OnboardingState
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 16.dp)
            .background(
                color = MainTheme.colors.containerBackground,
                shape = RoundedCornerShape(24.dp)
            ),
        horizontalArrangement = Arrangement.Center
    ) {
        val title = when (state.screenState) {
            OnboardingScreenState.WELCOME -> stringResource(Res.string.welcome)
            OnboardingScreenState.CHAT -> stringResource(Res.string.chatWithZub)
            OnboardingScreenState.TEETH -> stringResource(Res.string.teethType)
        }
        Text(
            text = title,
            color = MainTheme.colors.secondary,
            style = MainTheme.typography.onboarding.upperStateRow
        )
    }
}
