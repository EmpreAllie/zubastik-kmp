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
import com.features.ui.textInput.DisabledTextField
import com.features.ui.textInput.InputTextField
import com.features.ui.Res
import com.features.ui.enterNumber
import com.features.ui.ic_down
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
fun PhoneInputComponent(
    phoneNumber: PhoneNumber,
    onTextChange: (String) -> Unit,
    isError: Boolean,
) {
    Row(
        modifier = Modifier,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        DisabledTextField(
            modifier = Modifier.width(90.dp),
            text = phoneNumber.countryCode, // TODO ПОТОМ написать expect/actual-функцию и брать из нативных классов Androis/iOS
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
                //TODO потом onEvent(AuthEvents.OnCountryCodeClicked) - выбор кода страны
                // надо сохранить код страны в state.phoneNumber.countryCode
            }
        )

        InputTextField(
            modifier = Modifier.weight(1f),
            text = phoneNumber.number,
            hintText = stringResource(Res.string.enterNumber),
            isError = isError,
            keyboardType = KeyboardType.Phone,
            singleLine = true,
            maxLength = 10,
            textStyle = MainTheme.typography.auth.inputNumber.copy(color = MainTheme.colors.black),
            onTextChange = onTextChange,
            visualTransformation = PhoneVisualTransformation()
        )
    }
}