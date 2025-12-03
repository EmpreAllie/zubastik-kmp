package com.features.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import com.features.ui.Res
import com.features.ui.button.MainButton
import com.features.ui.ic_back
import com.features.ui.ic_logo_yandex
import com.features.ui.login
import com.features.ui.loginWithYandexID
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun LoginButtonContainer(
    modifier: Modifier = Modifier,
    onLoginClick: () -> Unit
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .clip(RoundedCornerShape(20.dp))
            .background(MainTheme.colors.white)
            .padding(24.dp)
    ) {
        MainButton(
            text = stringResource(Res.string.login),
            backgroundColor = MainTheme.colors.gray,
            contentColor = MainTheme.colors.black,
            onClick = onLoginClick
        )

        OrDivider(
            modifier = Modifier.padding(vertical = 8.dp) // внешний отступ
        )

        MainButton(
            text = stringResource(Res.string.loginWithYandexID),
            backgroundColor = MainTheme.colors.black,
            contentColor = MainTheme.colors.white,
            leadingIcon = {
                Image(
                    modifier = Modifier
                        .padding(horizontal = 10.dp)
                        .size(24.dp),
                    painter = painterResource(Res.drawable.ic_logo_yandex),
                    contentDescription = null,
                    alpha = 1.0f
                )
            },
            onClick = {}
        )
    }
}