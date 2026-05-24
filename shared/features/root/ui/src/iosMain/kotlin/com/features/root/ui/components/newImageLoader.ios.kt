package com.features.root.ui.components

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.memory.MemoryCache
import coil3.network.ktor3.KtorNetworkFetcherFactory
import coil3.request.crossfade
import coil3.util.DebugLogger
import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO

actual fun newImageLoader(context: PlatformContext, debug: Boolean): ImageLoader =
    ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, percent = 0.25)
                .build()
        }
        .components {
            add(KtorNetworkFetcherFactory { HttpClient(CIO) })
        }
        .crossfade(true)
        .apply {
            if (debug) logger(DebugLogger())
        }
        .build()
