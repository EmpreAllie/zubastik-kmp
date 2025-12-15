package com.features.auth.presentation.model

import com.entity.model.PhoneNumber
import com.features.auth.domain.model.LoginType

// Возможные события во флоу авторизации
// просто общее описание data object'ов, которые имеют тип "AuthEvents"
sealed interface AuthEvents {

    data class OnClickLogin(val type: LoginType) : AuthEvents

    // Событие при закрытии диалогового окна с ошибкой
    data object OnCloseDialog: AuthEvents

    // Событие при изменении текста в форме ввода телефона
    data class OnPhoneNumberChanged(val phoneNumber: PhoneNumber) : AuthEvents

    data object OnGotoCodeClicked: AuthEvents

    data object OnBackClicked: AuthEvents

    data class OnVerificationCodeChanged(val code: String): AuthEvents

    data object OnStartResendCodeTimer: AuthEvents

    data object OnResendCodeClicked: AuthEvents

}

