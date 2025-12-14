package com.features.ui.components

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.features.ui.ic_back_arrow
import com.features.ui.theme.MainTheme
import org.jetbrains.compose.resources.painterResource
import com.features.ui.Res


@Composable
fun TopBarBackButton(
    onBackClicked: () -> Unit
) {
    IconButton(
        modifier = Modifier.padding(start = 8.dp),
        onClick = onBackClicked
    ) {
        Icon(
            modifier = Modifier.size(32.dp),
            painter = painterResource(Res.drawable.ic_back_arrow),
            contentDescription = "Back",
            tint = MainTheme.colors.secondary
        )
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    // я не могу использовать BackButton, потому что это не класс, а функция
    // помечаем функцию как Composable, потому что будем вызывать нечто вроде Icon(), по умолчанию Null
    navigationIcon: @Composable (() -> Unit)? = null,
    colors: TopAppBarColors = TopAppBarDefaults.centerAlignedTopAppBarColors(
        containerColor = MainTheme.colors.transparent
    )
) {
    CenterAlignedTopAppBar(
        title = {
            Text(
                text = title,
                style = MainTheme.typography.auth.title,
                color = MainTheme.colors.secondary
            )
        },
        navigationIcon = {
            navigationIcon?.invoke()
        },
        colors = colors
    )
}