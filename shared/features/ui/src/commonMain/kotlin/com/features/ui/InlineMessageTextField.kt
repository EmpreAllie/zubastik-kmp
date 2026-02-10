package com.features.ui

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.font.FontWeight
import com.features.ui.theme.MainTheme

@Composable
fun InlineMessageTextField(
    text: String,
    onTextChange: (String) -> Unit,
    hint: String,
    prefix: String,
    modifier: Modifier = Modifier
) {
    BasicTextField(
        value = text,
        onValueChange = onTextChange,
        modifier = modifier,
        textStyle = MainTheme.typography.message.text.copy(
            fontWeight = FontWeight.Bold
        ),
        cursorBrush = SolidColor(MainTheme.colors.secondary),
        decorationBox = { innerTextField ->
            Row(
                verticalAlignment = Alignment.CenterVertically
            ) {
                // 3. Рисуем наш префикс
                Text(
                    text = prefix,
                    style = MainTheme.typography.message.text,
                    color = MainTheme.colors.white
                )

                // 4. Контейнер для поля ввода и подсказки
                Box {
                    if (text.isEmpty()) {
                        Text(
                            text = hint,
                            style = MainTheme.typography.message.text,
                            color = MainTheme.colors.white.copy(alpha = 0.5f)
                        )
                    }
                    // 5. Вызываем фактическое поле для ввода
                    innerTextField()
                }
            }
        }
    )
}