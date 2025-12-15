package com.features.base.domain.model.error

import com.features.base.domain.Result

sealed interface Error {
    data object CONNECTION : Error
    data object TOKEN : Error

    data class OTHER(val message: String) : Error
    data class AUTH(val type: AuthErrorType) : Error

    fun toResult() = Result.Failure(this)
    fun isRecoverableError() = when (this) {
        TOKEN, CONNECTION -> true
        else -> false
    }
}