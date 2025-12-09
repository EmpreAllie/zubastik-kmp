package com.features.auth.presentation.model

// Возможные события во флоу авторизации
// просто общее описание data object'ов, которые имеют тип "AuthEvents"
sealed interface AuthEvents {

    // один из видов AuthEvents, Singleton - будет создан только один экземпляр
    data object OnLoginClicked : AuthEvents

    data object OnYandexLoginClicked : AuthEvents

    // Событие при закрытии диалогового окна с ошибкой
    data object OnCloseDialog: AuthEvents

    // Событие при изменении текста в форме ввода телефона
    data class OnPhoneNumberChanged(val number: String, val countryCode: String) : AuthEvents

    data object OnGotoCodeClicked: AuthEvents

    data object OnBackClicked: AuthEvents

    data class OnVerificationCodeChanged(val code: String): AuthEvents

    data object OnCodeVerificationStarted: AuthEvents

    data object OnStartResendCodeTimer: AuthEvents

    data object OnResendCodeClicked: AuthEvents

}

