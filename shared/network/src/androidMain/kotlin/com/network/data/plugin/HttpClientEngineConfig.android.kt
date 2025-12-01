package com.network.data.plugin

import io.ktor.client.engine.*
import io.ktor.client.engine.okhttp.*
import java.util.concurrent.TimeUnit

actual fun createHttpClientEngine(block: HttpClientEngineConfig.() -> Unit): HttpClientEngine {
    val config = HttpClientEngineConfig().also(block)
    return OkHttp.create {
        this.config {
            config.connectTimeoutSeconds?.let { connectTimeout(it, TimeUnit.SECONDS) }
            config.callTimeoutSeconds?.let { callTimeout(it, TimeUnit.SECONDS) }
            config.readTimeoutSeconds?.let { readTimeout(it, TimeUnit.SECONDS) }
            config.writeTimeoutSeconds?.let { writeTimeout(it, TimeUnit.SECONDS) }
        }
    }
}