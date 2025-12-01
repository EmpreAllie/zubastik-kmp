package com.features.base.domain.model

enum class Error {
    CONNECTION,
    TOKEN,
    OTHER;

    var message: String? = null
        private set

    companion object {
        fun other(message: String): Error = OTHER.apply { this.message = message }
    }
}

