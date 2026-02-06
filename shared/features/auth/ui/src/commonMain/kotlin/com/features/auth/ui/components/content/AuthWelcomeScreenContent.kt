package com.features.auth.ui.components.content

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.features.auth.presentation.model.AuthEvents
import com.features.auth.presentation.model.AuthState
import com.features.auth.ui.components.LoginButtonContainer
import com.features.ui.components.AppLogo
import com.features.ui.theme.MainTheme

@Composable
fun AuthWelcomeScreenContent(
    state: AuthState,
    onEvent: (AuthEvents) -> Unit
) {
    Scaffold (
        containerColor = MainTheme.colors.primary
    ) { padding -> // параметр из анонимной лямбда-функции
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding) // PaddingValues из Scaffold(), которые зависят от объектов внутри Scaffold
                .padding(horizontal = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Content - последний параметр, trailing lambda у Column()

            AppLogo()

            Spacer(modifier = Modifier.height(32.dp))

            LoginButtonContainer(
                modifier = Modifier.padding(horizontal = 12.dp), // внешний отступ у контейнера
                isLoading = state.isLoading,
                onClickLogin = {
                    onEvent(AuthEvents.OnClickLogin(it))
                },
            )


        }
    }
}