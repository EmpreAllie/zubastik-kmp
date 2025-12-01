package com.network.data.plugin

import io.ktor.client.engine.*
import io.ktor.client.engine.darwin.*
import platform.Foundation.*

actual fun createHttpClientEngine(block: HttpClientEngineConfig.() -> Unit): HttpClientEngine {
    val config = HttpClientEngineConfig().also(block)

    val sessionConfiguration = NSURLSessionConfiguration.defaultSessionConfiguration().apply {
        config.connectTimeoutSeconds?.let { timeoutIntervalForRequest = it.toDouble() }
        config.callTimeoutSeconds?.let { timeoutIntervalForResource = it.toDouble() }
    }

    return Darwin.create {
        configureSession {
            NSURLSessionConfiguration.defaultSessionConfiguration().apply {
                config.connectTimeoutSeconds?.let { timeoutIntervalForRequest = it.toDouble() }
                config.callTimeoutSeconds?.let { timeoutIntervalForResource = it.toDouble() }
            }
        }
        sessionConfig(sessionConfiguration)
    }
}
