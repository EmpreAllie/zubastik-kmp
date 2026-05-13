package com.features.base.domain.model.error

import androidx.compose.runtime.Composable
import com.features.base.domain.Result
import com.features.ui.Res
import com.features.ui.codeError
import com.features.ui.connectionError
import com.features.ui.otherError
import com.features.ui.phoneError
import org.jetbrains.compose.resources.stringResource


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