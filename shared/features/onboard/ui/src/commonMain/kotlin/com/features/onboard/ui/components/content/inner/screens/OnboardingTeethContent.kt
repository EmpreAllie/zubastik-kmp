package com.features.onboard.ui.components.content.inner.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.teeth.presentation.TeethViewModel
import com.features.teeth.ui.mappanel.InteractiveTeethMap
import com.features.ui.Res
import com.features.ui.button.MainButton
import com.features.ui.consent
import com.features.ui.nextStar
import com.features.ui.teethProfileText1
import com.features.ui.teethProfileText2
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun OnboardingTeethContent(
    onEvent: (OnboardingEvents) -> Unit,
    teethViewModel: TeethViewModel = koinViewModel(),
) {
    val teethState by teethViewModel.state.collectAsState()
    val scrollState = rememberScrollState()
    Column(
        modifier =
            Modifier
                .fillMaxSize()
                .verticalScroll(scrollState),
    ) {
        // текст "Заполни зубной профиль"
        Text(
            modifier =
                Modifier
                    .padding(top = 24.dp, bottom = 16.dp),
            text = stringResource(Res.string.teethProfileText1),
            style = MainTheme.typography.onboarding.chatContainerContent,
            color = MainTheme.colors.secondary,
            textAlign = TextAlign.Left,
        )
        Text(
            modifier =
                Modifier
                    .padding(bottom = 24.dp),
            text = stringResource(Res.string.teethProfileText2),
            style = MainTheme.typography.onboarding.chatContainerContent,
            color = MainTheme.colors.secondary,
            textAlign = TextAlign.Left,
        )

        InteractiveTeethMap(
            state = teethState,
            onEvent = teethViewModel::onEvent,
        )

        MainButton(
            modifier = Modifier.padding(top = 24.dp),
            text = stringResource(Res.string.nextStar),
            textStyle = MainTheme.typography.onboarding.buttonText,
            backgroundColor = MainTheme.colors.white,
            contentColor = MainTheme.colors.secondary,
            onClick = {
                onEvent(OnboardingEvents.OnExitOnboardingClicked)
            },
        )

        Text(
            modifier = Modifier.padding(top = 16.dp, bottom = 24.dp),
            text = stringResource(Res.string.consent),
            style = MainTheme.typography.onboarding.consent,
            color = MainTheme.colors.gray,
            textAlign = TextAlign.Justify,
        )
    }
}
