package com.features.auth.presentation.model

import com.entity.model.PhoneNumber
import com.features.auth.domain.model.LoginType

// Возможные события во флоу авторизации
// просто общее описание data object'ов, которые имеют тип "AuthEvents"
sealed interface AuthEvents {

    // Ивенты для AuthWelcomeViewModel
    data class OnClickLogin(val type: LoginType) : AuthEvents


    // Ивенты для AuthPhoneViewModel
    data class OnPhoneNumberChanged(val phoneNumber: PhoneNumber) : AuthEvents // Событие при изменении текста в форме ввода телефона
    data object OnGotoCodeClicked: AuthEvents


    // Ивенты для AuthCodeViewModel
    data class OnVerificationCodeChanged(val code: String): AuthEvents
    data object OnResendCodeClicked: AuthEvents
    data object OnStartResendCodeTimer: AuthEvents


    // Общие ивенты
    data object OnCloseDialog: AuthEvents // Событие при закрытии диалогового окна с ошибкой
    data object OnBackClicked: AuthEvents
}

