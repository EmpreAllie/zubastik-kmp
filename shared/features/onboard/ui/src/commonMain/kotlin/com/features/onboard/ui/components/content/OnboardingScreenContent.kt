package com.features.onboard.ui.components.content

import androidx.compose.foundation.background
import androidx.compose.foundation.focusable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.features.onboard.presentation.model.OnboardingEvents
import com.features.onboard.presentation.model.OnboardingScreenState
import com.features.onboard.presentation.model.OnboardingState
import com.features.onboard.ui.components.content.inner.OnboardingWelcomeContent
import com.features.onboard.ui.components.content.inner.UpperStateRow
import com.features.ui.Res
import com.features.ui.back
import com.features.ui.button.MainButton
import com.features.ui.next
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingScreenContent(
    state: OnboardingState,
    onEvent: (OnboardingEvents) -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(MainTheme.colors.primary)
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .focusable()
                .padding(top = 40.dp, start = 16.dp, end = 16.dp, bottom = 16.dp)
        ) {

            // Верхняя плашка
            UpperStateRow(state = state)

            Spacer(modifier = Modifier.height(24.dp))

            // Контейнер для чата/всего остального
            when (state.screenState) {
                OnboardingScreenState.WELCOME -> {
                    OnboardingWelcomeContent()
                }

                OnboardingScreenState.CHAT -> {
                    // ChatContent()
                }

                OnboardingScreenState.TEETH -> {
                    // TeethContent()
                }
            }



            Spacer(modifier = Modifier.height(24.dp))


            // Кнопки навигации
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 16.dp),
                horizontalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                MainButton(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(Res.string.back),
                    textStyle = MainTheme.typography.onboarding.buttonText,
                    shape = RoundedCornerShape(10.dp),
                    contentColor = MainTheme.colors.secondary,
                    backgroundColor = MainTheme.colors.containerBackground,
                    onClick = {

                    }
                )

                MainButton(
                    modifier = Modifier
                        .weight(1f),
                    text = stringResource(Res.string.next),
                    textStyle = MainTheme.typography.onboarding.buttonText,
                    shape = RoundedCornerShape(10.dp),
                    contentColor = MainTheme.colors.containerBackground,
                    backgroundColor = MainTheme.colors.secondary,
                    onClick = {

                    }
                )
            }
        }
    }
}