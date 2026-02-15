package com.features.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Divider
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.stringResource

@Composable
fun InlineMessageTextField(
    text: String,
    onTextChange: (String) -> Unit,
    hint: String,
    prefix: String? = null,
    postfix: String? = null,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = text,
        onValueChange = { newText ->
            if (newText.length <= 15)
                onTextChange(newText)
        },
        modifier = modifier,
        enabled = enabled,
        textStyle = MainTheme.typography.message.text.copy(
            fontWeight = FontWeight.Bold,
            color = MainTheme.colors.secondary
        ),
        cursorBrush = SolidColor(MainTheme.colors.secondary),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                prefix?.let {
                    Text(
                        text = prefix,
                        style = MainTheme.typography.message.text,
                        color = MainTheme.colors.secondary
                    )
                }

                Spacer(modifier = Modifier.width(4.dp))

                Column {
                    Box {
                        if (text.isEmpty()) {
                            Text(
                                text = hint,
                                style = MainTheme.typography.message.text,
                                color = MainTheme.colors.secondary.copy(alpha = 0.5f)
                            )
                        }

                        innerTextField()
                    }

                    HorizontalDivider(
                        modifier = Modifier.width(90.dp),
                        thickness = 2.dp,
                        color = MainTheme.colors.secondary
                    )
                }

                postfix?.let {
                    Spacer(modifier = Modifier.width(4.dp))
                    Text(
                        text = it,
                        style = MainTheme.typography.message.text,
                        color = MainTheme.colors.secondary
                    )
                }
            }
        }
    )
}