package com.entity.model

data class PhoneNumber(
    val countryCode: String = "+7",
    val number: String = ""
) {

    fun format() = "$countryCode$number" // TODO: Format to "+X (XXX) XXX-XX-XX

    fun isCorrect(): Boolean {
        val phoneNumber = format()
        return (phoneNumber.startsWith("+79") || phoneNumber.startsWith("8"))
                && phoneNumber.length == 12
    }

}
