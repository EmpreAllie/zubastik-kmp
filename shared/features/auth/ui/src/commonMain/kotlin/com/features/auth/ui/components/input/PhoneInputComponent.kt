package com.features.auth.ui.components.input

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import com.entity.model.PhoneNumber
import com.features.ui.DisabledTextField
import com.features.ui.InputTextField
import com.features.ui.Res
import com.features.ui.enterNumber
import com.features.ui.ic_down
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PhoneInputComponent(
    modifier: Modifier = Modifier,
    phoneNumber: PhoneNumber,
    onTextChange: (String) -> Unit,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        DisabledTextField(
            modifier = Modifier.width(90.dp),
            text = phoneNumber.countryCode, // TODO брать откуда-то еще
            hintText = "",
            textStyle = MainTheme.typography.auth.inputNumber,
            trailingIcon = {
                Icon(
                    painter = painterResource(Res.drawable.ic_down),
                    contentDescription = "Down Arrow",
                    tint = MainTheme.colors.black.copy(alpha = 0.5f)
                )
            },
            onClick = {
                //TODO onEvent(AuthEvents.OnCountryCodeClicked) - выбор кода страны
                // надо сохранить код страны в в state.countryCode
            }
        )

        InputTextField(
            modifier = Modifier.weight(1f),
            text = phoneNumber.number,
            hintText = stringResource(Res.string.enterNumber),
            isError = phoneNumber.isCorrect(),
            keyboardType = KeyboardType.Phone,
            singleLine = true,
            maxLength = 10,
            textStyle = MainTheme.typography.auth.inputNumber.copy(color = MainTheme.colors.black),
            onTextChange = onTextChange
        )
    }
}