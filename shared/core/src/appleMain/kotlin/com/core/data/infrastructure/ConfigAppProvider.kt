package com.widmeyertemplate.core.data.infrastructure

expect class ConfigAppProvider(keyValueStorage: com.core.data.infrastructure.KeyValueStorage) {
    val versionApp: String
    val keyValueStorage: com.core.data.infrastructure.KeyValueStorage
}