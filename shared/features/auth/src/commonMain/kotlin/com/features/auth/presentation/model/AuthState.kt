package com.features.auth.presentation.model

import com.features.base.domain.model.Error
import com.features.base.presentation.model.BaseState

enum class VerificationStatus {
    NEUTRAL,
    SUCCESS,
    ERROR
}


// State хранит данные, которые нужны для отображения экрана

// это data class, потому что мы будем создавать его копии с обновленными полями через метод copy().
// например, при изменении статуса ошибки мы будем очищать её текст (присвоив параметр error = null)
data class AuthState(
    override val error: Error? = null,
    override val isLoading: Boolean = false,

    // номер мобилы
    val countryCode: String = "+7",
    val phoneNumber: String = "",
    val isPhoneNumberError: Boolean = false,

    // код
    val verificationCode: String = "",
    val verificationStatus: VerificationStatus = VerificationStatus.NEUTRAL,
    val resendCodeTimerSeconds: Int = 59,

    // ошибки
    val isConnectionError: Boolean = false,
    val errorMessage: String? = null

) : BaseState(isLoading = isLoading, error = error) {

}