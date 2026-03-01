package com.features.onboard.ui.components.content.inner

import androidx.compose.foundation.background
import androidx.compose.foundation.gestures.scrollable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.teeth.ui.TeethMapPanel
import com.features.ui.Res
import com.features.ui.button.MainButton
import com.features.ui.consent
import com.features.ui.next
import com.features.ui.nextStar
import com.features.ui.teethProfileText1
import com.features.ui.teethProfileText2
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingTeethContent(
    onEvent: (OnboardingEvents) -> Unit
) {
    val scrollState = rememberScrollState()
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(scrollState)
    ) {
        // текст "Заполни зубной профиль"
        Text(
            modifier = Modifier
               .padding(top = 24.dp, bottom = 16.dp),
            text = stringResource(Res.string.teethProfileText1),
            style = MainTheme.typography.onboarding.chatContainerContent,
            color = MainTheme.colors.secondary,
            textAlign = TextAlign.Left
        )
        Text(
            modifier = Modifier
                .padding(bottom = 24.dp),
            text = stringResource(Res.string.teethProfileText2),
            style = MainTheme.typography.onboarding.chatContainerContent,
            color = MainTheme.colors.secondary,
            textAlign = TextAlign.Left
        )

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .clip(RoundedCornerShape(24.dp))
                .background(MainTheme.colors.white)
                .padding(16.dp)
        ) {
            TeethMapPanel()
        }

        MainButton(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(Res.string.nextStar),
            textStyle = MainTheme.typography.onboarding.buttonText,
            backgroundColor = MainTheme.colors.white,
            contentColor = MainTheme.colors.secondary,
            onClick = {
                onEvent(OnboardingEvents.OnExitOnboardingClicked)
            }
        )

        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
            text = stringResource(Res.string.consent),
            style = MainTheme.typography.onboarding.consent,
            color = MainTheme.colors.gray,
            textAlign = TextAlign.Justify
        )



    }
}