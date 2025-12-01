package com.network.data.plugin

import io.ktor.client.engine.HttpClientEngine

class HttpClientEngineConfig {
    var connectTimeoutSeconds: Long? = 20
    var callTimeoutSeconds: Long? = 20
    var readTimeoutSeconds: Long? = 20
    var writeTimeoutSeconds: Long? = 20
}

expect fun createHttpClientEngine(block: HttpClientEngineConfig.() -> Unit): HttpClientEngine