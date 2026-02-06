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


    @Composable
    fun getDisplayMessage(): String {
        return when (this) {
            CONNECTION -> stringResource(Res.string.connectionError)

            is AUTH -> when (this.type) {
                AuthErrorType.PHONE -> stringResource(Res.string.phoneError)
                AuthErrorType.CODE -> stringResource(Res.string.codeError)
            }

            else -> stringResource(Res.string.otherError)
        }
    }
}