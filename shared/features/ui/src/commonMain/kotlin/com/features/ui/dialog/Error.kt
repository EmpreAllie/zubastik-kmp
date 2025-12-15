package com.features.ui.dialog

import androidx.compose.runtime.Composable
import com.features.base.domain.model.error.AuthErrorType
import com.features.base.domain.model.error.Error
import com.features.ui.Res
import com.features.ui.appName
import org.jetbrains.compose.resources.stringResource

@Composable
fun Error.toText() = when (this) {
    Error.CONNECTION -> stringResource(Res.string.appName) // TODO: Change text to String Resource
    Error.TOKEN -> stringResource(Res.string.appName) // TODO: Change text to Token
    is Error.OTHER -> message.ifBlank { stringResource(Res.string.appName) } // TODO: Change default text
    is Error.AUTH -> this.toText()
}

@Composable
fun Error.AUTH.toText() = when (type) {
    AuthErrorType.PHONE -> stringResource(Res.string.appName) // TODO: Change text to PhoneNumber
    AuthErrorType.CODE -> stringResource(Res.string.appName) // TODO: Change text to Code
}