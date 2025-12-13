package com.features.ui.components

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.TopAppBarColors
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AppTopBar(
    title: String,
    // я не могу использовать BackButton, потому что это не класс, а функция
    // помечаем функцию как Composable, потому что будем вызывать нечто вроде Icon(), по умолчанию Null
    backButton: @Composable (() -> Unit)? = null,
    colors: TopAppBarColors = TopAppBarDefaults.topAppBarColors()
) {

}