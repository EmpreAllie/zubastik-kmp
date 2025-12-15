package com.features.auth.ui.components.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.presentation.model.AuthState
import com.features.auth.ui.components.input.CodeInputComponent
import com.features.ui.Res
import com.features.ui.code
import com.features.ui.components.AppTopBar
import com.features.ui.components.TopBarBackButton
import com.features.ui.enterCode
import com.features.ui.sendCodeAgainTextButton
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AuthCodeScreenContent(
    state: AuthState,
    onEvent: (AuthEvents) -> Unit
) {

    Scaffold(
        containerColor = MainTheme.colors.primary,

        topBar = {
            AppTopBar(
                title = stringResource(Res.string.code),
                navigationIcon = {
                    TopBarBackButton {
                        onEvent(AuthEvents.OnBackClicked)
                    }
                }
            )
            /*
            CenterAlignedTopAppBar(
                title = {
                    Text(
                        text = stringResource(Res.string.code),
                        style = MainTheme.typography.auth.title,
                        color = MainTheme.colors.secondary
                    )
                },
                navigationIcon = {
                    IconButton(
                        modifier = Modifier.padding(start = 8.dp),
                        onClick = {
                            onEvent(AuthEvents.OnBackClicked)
                        }
                    ) {
                        Icon(
                            modifier = Modifier.size(32.dp),
                            painter = painterResource(Res.drawable.ic_back_arrow),
                            contentDescription = "Back",
                            tint = MainTheme.colors.secondary
                        )
                    }
                },
                colors = TopAppBarDefaults.centerAlignedTopAppBarColors(
                    containerColor = MainTheme.colors.transparent
                )
            )
            */
        }
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
                modifier = Modifier
                    .fillMaxWidth(),
                text = stringResource(Res.string.enterCode),
                style = MainTheme.typography.auth.secondary,
                color = MainTheme.colors.secondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(8.dp))

            Text(
                modifier = Modifier
                    .fillMaxWidth(),
                text = state.phoneNumber.format(),
                style = MainTheme.typography.auth.phoneNumberOnCodeScreen,
                color = MainTheme.colors.secondary,
                textAlign = TextAlign.Center
            )

            Spacer(modifier = Modifier.height(24.dp))

            CodeInputComponent(
                code = state.verificationCode,
                onCodeChanged = { newCode ->
                    onEvent(AuthEvents.OnVerificationCodeChanged(newCode))
                },
                verificationStatus = state.verificationStatus
            )

            Spacer(modifier = Modifier.height(24.dp))

            // Обновление цифры с секундами
            // TODO stringResource для текста
            // TODO Секунды/секунд/секунду - Plurals
            if (state.resendCodeTimerSeconds > 0) {
                Text(
                    modifier = Modifier.fillMaxWidth(),
                    text = "Повторно отправить код можно через ${state.resendCodeTimerSeconds} секунд",
                    style = MainTheme.typography.auth.sendCodeAgain,
                    color = MainTheme.colors.secondary,
                    textAlign = TextAlign.Center
                )
            }
            else {
                TextButton(onClick = { onEvent(AuthEvents.OnResendCodeClicked) }) {
                    Text(
                        text = stringResource(Res.string.sendCodeAgainTextButton),
                        style = MainTheme.typography.auth.sendCodeAgain,
                        color = MainTheme.colors.secondary,
                        textAlign = TextAlign.Center
                    )
                }
            }
        }
    }
}