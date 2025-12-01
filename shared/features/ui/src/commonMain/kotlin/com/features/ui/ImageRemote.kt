package com.features.ui

import androidx.compose.foundation.Image
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.FilterQuality
import androidx.compose.ui.layout.ContentScale
import coil3.compose.SubcomposeAsyncImage
import org.jetbrains.compose.resources.DrawableResource
import org.jetbrains.compose.resources.painterResource

@Composable
fun ImageRemote(
    modifier: Modifier,
    url: String,
    placeHolder: DrawableResource,
    error: DrawableResource,
    contentScale: ContentScale = ContentScale.Fit,
    contentDescription: String? = null,
) {
    SubcomposeAsyncImage(
        modifier = modifier,
        model = url,
        loading = {
            Image(
                painter = painterResource(placeHolder),
                contentDescription = null
            )
        },
        error = {
            Image(
                painter = painterResource(error),
                contentDescription = null
            )
        },
        contentDescription = contentDescription,
        contentScale = contentScale,
        alignment = Alignment.Center,
        filterQuality = FilterQuality.Medium
    )
}