package com.features.teeth.domain.model

enum class ToothType {
    INCISOR, // резец
    CANINE, // Клык
    PREMOLAR, // премоляр
    MOLAR, // моляр
    WISDOM; // зуб мудрости

    companion object {
        fun fromPosition(pos: Int) = when(pos % 10) {
            1, 2 -> INCISOR
            3 -> CANINE
            4, 5 -> PREMOLAR
            6, 7 -> MOLAR
            8 -> WISDOM

            else -> INCISOR // мы никогда не попадём сюда, но это надо для when с числами
        }
    }
}