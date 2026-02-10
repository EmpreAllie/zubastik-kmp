package com.features.onboard.ui.components.content.inner

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.dp
import org.jetbrains.compose.resources.painterResource
import com.features.ui.Res
import com.features.ui.onboardingInfo1
import com.features.ui.onboardingInfo1_1
import com.features.ui.onboardingInfo1_2
import com.features.ui.onboardingInfo2
import com.features.ui.theme.MainTheme
import com.features.ui.zub
import com.features.ui.zubastik
import org.jetbrains.compose.resources.stringResource

@Composable
fun OnboardingWelcomeContent() {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {

        Image(
            modifier = Modifier
                .size(130.dp)
                .padding(top = 32.dp),
            painter = painterResource(Res.drawable.zub),
            contentDescription = "Zubastik Image"
        )

        Text(
            modifier = Modifier
                .padding(top = 24.dp, bottom = 24.dp),
            text = buildAnnotatedString {
                append(stringResource(Res.string.onboardingInfo1_1))
                withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                    append(stringResource(Res.string.zubastik))
                }
                append(stringResource(Res.string.onboardingInfo1_2))
            },
            style = MainTheme.typography.onboarding.chatContainerContent,
            color = MainTheme.colors.secondary,
            textAlign = TextAlign.Left
        )


        Text(
            modifier = Modifier
                .padding(top = 8.dp, bottom = 24.dp),
            text = stringResource(Res.string.onboardingInfo2),
            style = MainTheme.typography.onboarding.chatContainerContent,
            color = MainTheme.colors.secondary,
            textAlign = TextAlign.Left
        )
    }
}