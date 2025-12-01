package com.features.ui

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import com.features.ui.theme.MainTheme

/**
 * @param modifier Модификатор для настройки свойств или размеров компонента
 * @param text Первая строка в App Bar
 * @param subText Описание (вторая строка) в App Bar
 * @param navigationIcon Элементы слева от текста (например, кнопка "Назад")
 * @param action Элементы справа от текста (например, кнопка "Домой")
 */

@OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    modifier: Modifier = Modifier,
    text: String? = null,
    subText: String? = null,
    navigationIcon: @Composable () -> Unit = {},
    action: @Composable RowScope.() -> Unit = {}
) {
    TopAppBar(
        modifier = modifier,
        title = {
            Column(
                verticalArrangement = Arrangement.Center
            ) {
                text?.let {
                    Text(
                        modifier = Modifier,
                        text = text,
                        style = MainTheme.typography.main.title,
                        overflow = TextOverflow.Ellipsis,
                        maxLines = 1
                    )
                    subText?.let {
                        Text(
                            text = it,
                            style = MainTheme.typography.main.main,
                            overflow = TextOverflow.Ellipsis,
                            maxLines = 1
                        )
                    }
                }
            }
        },
        navigationIcon = navigationIcon,
        actions = action,
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MainTheme.colors.transparent,
            titleContentColor =  MainTheme.colors.thirdly,
            navigationIconContentColor = MainTheme.colors.thirdly,
            actionIconContentColor = MainTheme.colors.thirdly
        )
    )
}

@Composable
fun IconButtonBackArrow(onClick: () -> Unit) {
    // TODO: ImageButton
}