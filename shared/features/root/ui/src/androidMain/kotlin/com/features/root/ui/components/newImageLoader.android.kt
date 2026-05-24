package com.features.root.ui.components

import coil3.ImageLoader
import coil3.PlatformContext
import coil3.memory.MemoryCache
import coil3.network.okhttp.OkHttpNetworkFetcherFactory
import okhttp3.OkHttpClient
import java.util.concurrent.TimeUnit
import coil3.request.crossfade
import coil3.util.DebugLogger

actual fun newImageLoader(context: PlatformContext, debug: Boolean): ImageLoader =
    ImageLoader.Builder(context)
        .memoryCache {
            MemoryCache.Builder()
                .maxSizePercent(context, percent = 0.25)
                .build()
        }
        .components {
            add(OkHttpNetworkFetcherFactory(
                callFactory = OkHttpClient.Builder()
                    .connectTimeout(30, TimeUnit.SECONDS)
                    .readTimeout(60, TimeUnit.SECONDS)
                    .build()
            ))
        }
        .crossfade(true)
        .apply {
            if (debug) logger(DebugLogger())
        }
        .build()
