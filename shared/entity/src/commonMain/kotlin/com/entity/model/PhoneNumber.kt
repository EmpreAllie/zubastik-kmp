package com.entity.model

data class PhoneNumber(
    val countryCode: String = "+7",
    val number: String = ""
) {

    fun format(): String {

        if (number.isBlank())
            return countryCode

        val formattedNumber = buildString {
            number.take(3).also { if (it.isNotEmpty()) append(" ($it)") }
            number.drop(3).take(3).also { if (it.isNotEmpty()) append(" $it") }
            number.drop(6).take(2).also { if (it.isNotEmpty()) append("-$it") }
            number.drop(8).take(2).also { if (it.isNotEmpty()) append("-$it") }
        }

        return "$countryCode$formattedNumber"
    }

    fun toE164(): String = "$countryCode$number"

    fun isCorrect(): Boolean {
        // Окей, эта функция отрабатывает правильно и возаращает TRUE только тогда, когда номер полностью введен и начинается с 9
        return number.length == 10 && number.startsWith("9")
    }

}
