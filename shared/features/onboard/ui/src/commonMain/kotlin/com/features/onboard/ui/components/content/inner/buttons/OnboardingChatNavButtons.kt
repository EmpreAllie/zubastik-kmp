package com.features.onboard.ui.components.content.inner.buttons

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.onboard.presentation.model.state.OnboardingStep
import com.features.ui.Res
import com.features.ui.back
import com.features.ui.button.MainButton
import com.features.ui.finish
import com.features.ui.next
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingChatNavButtons(
    curStep: OnboardingStep,
    userTextInput: String,
    onBackClicked: () -> Unit,
    onNextClicked: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = 32.dp),
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        // "Назад"
        MainButton(
            modifier = Modifier.weight(1f),
            text = stringResource(Res.string.back),
            textStyle = MainTheme.typography.onboarding.buttonText,
            shape = RoundedCornerShape(10.dp),
            contentColor = MainTheme.colors.secondary,
            backgroundColor = MainTheme.colors.containerBackground,
            onClick = onBackClicked
        )

        // "Далее"
        MainButton(
            modifier = Modifier.weight(1f),
            text = if (curStep == OnboardingStep.COMPLETE)
                stringResource(Res.string.finish)
            else
                stringResource(Res.string.next),
            textStyle = MainTheme.typography.onboarding.buttonText,
            shape = RoundedCornerShape(10.dp),
            contentColor = MainTheme.colors.containerBackground,
            backgroundColor = MainTheme.colors.secondary,
            isEnabled = userTextInput.isNotEmpty(),
            onClick = onNextClicked
        )
    }
}