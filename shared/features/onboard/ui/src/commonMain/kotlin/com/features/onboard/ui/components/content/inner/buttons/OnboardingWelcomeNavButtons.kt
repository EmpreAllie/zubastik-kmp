package com.features.onboard.ui.components.content.inner.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.ui.Res
import com.features.ui.alreadyFamiliar
import com.features.ui.begin
import com.features.ui.button.MainButton
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingWelcomeNavButtons(
    onStartChatClicked: () -> Unit,
    onAlreadyFamiliarClicked: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        verticalArrangement = Arrangement.spacedBy(12.dp),
    ) {
        // "Начать"
        MainButton(
            modifier = Modifier
                .fillMaxWidth(),
            text = stringResource(Res.string.begin),
            textStyle = MainTheme.typography.onboarding.buttonText,
            shape = RoundedCornerShape(10.dp),
            contentColor = MainTheme.colors.secondary,
            backgroundColor = MainTheme.colors.containerBackground,
            onClick = onStartChatClicked
        )
        // "Мы уже знакомы"
        MainButton(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 24.dp),
            text = stringResource(Res.string.alreadyFamiliar),
            textStyle = MainTheme.typography.onboarding.alreadyFamiliar,
            shape = RoundedCornerShape(10.dp),
            contentColor = MainTheme.colors.containerBackground,
            backgroundColor = MainTheme.colors.secondary,
            onClick = onAlreadyFamiliarClicked
        )
    }
}