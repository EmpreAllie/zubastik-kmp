package com.features.auth.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ModifierLocalBeyondBoundsLayout
import androidx.compose.ui.unit.dp
import com.features.ui.button.MainButton
import com.features.ui.components.AppLogo
import com.features.ui.components.LoginButtonContainer
import com.features.ui.components.OrDivider
import com.features.ui.theme.MainTheme

@Composable
fun AuthWelcomeScreenContent(
    onLoginClick: () -> Unit
) {
    Scaffold (
        containerColor = MainTheme.colors.primary
    )
    // ... , content =
    { padding -> // параметр из лямбда-функции
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

            Spacer(
                modifier = Modifier.height(32.dp)
            )

            LoginButtonContainer(
                modifier = Modifier.padding(horizontal = 12.dp), // внешний отступ у контейнера
                onLoginClick = onLoginClick
            )


        }
    }
}