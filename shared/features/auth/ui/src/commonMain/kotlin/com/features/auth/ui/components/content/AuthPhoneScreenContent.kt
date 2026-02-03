package com.features.auth.ui.components.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.presentation.model.AuthState
import com.features.auth.ui.components.input.PhoneInputComponent
import com.features.ui.Res
import com.features.ui.button.MainButton
import com.features.ui.enterPhoneNumber
import com.features.ui.next
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun AuthPhoneScreenContent(
    state: AuthState,
    onEvent: (AuthEvents) -> Unit
) {
    Scaffold(
        containerColor = MainTheme.colors.primary
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 32.dp)
                .imePadding(),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {

            Text(
                modifier = Modifier.fillMaxWidth(),
                text = stringResource(Res.string.enterPhoneNumber),
                style = MainTheme.typography.auth.title,
                color = MainTheme.colors.secondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(48.dp))


            PhoneInputComponent(
                phoneNumber = state.phoneNumber,
                onTextChange = { value ->
                    onEvent(AuthEvents.OnPhoneNumberChanged(state.phoneNumber.copy(number = value)))
                }
            )


            Spacer(modifier = Modifier.height(56.dp))

            MainButton(
                text = stringResource(Res.string.next),
                backgroundColor = MainTheme.colors.secondary,
                contentColor = MainTheme.colors.white,
                isEnabled = state.phoneNumber.isCorrect(),
                onClick = { onEvent(AuthEvents.OnGotoCodeClicked) }
            )
        }
    }
}