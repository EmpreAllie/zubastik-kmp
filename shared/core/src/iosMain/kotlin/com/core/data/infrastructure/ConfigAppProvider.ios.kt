package com.widmeyertemplate.core.data.infrastructure

import com.core.data.infrastructure.KeyValueStorage

actual class ConfigAppProvider actual constructor(actual val keyValueStorage: KeyValueStorage) {
    actual val versionApp: String
        get() = TODO("Not yet implemented")
}