package com.features.ui.theme

import androidx.compose.ui.graphics.Color

sealed class Colors {
    abstract val primary: Color
    abstract val secondary: Color
    abstract val thirdly: Color
    abstract val disabled: Color
    abstract val disabledContent: Color
    abstract val success: Color
    abstract val error: Color
    abstract val border: Color
    abstract val white: Color
    abstract val black: Color
    abstract val red: Color
    abstract val gray: Color
    abstract val darkGray: Color
    abstract val lightGray: Color
    abstract val green: Color
    abstract val transparent: Color
    abstract val button: Color
    abstract val selectedButton: Color
    abstract val containerBackground: Color
    abstract val toothStroke: Color
    abstract val toothSelected: Color
    abstract val toothProblematic: Color
    abstract val toothMissing: Color
    abstract val bottomNavBarBackground: Color
    abstract val eventBlue: Color
    abstract val eventRed: Color
    abstract val eventLightBlue: Color
    abstract val eventOrange: Color
    abstract val eventTiffany: Color
    abstract val weekDaysGray: Color
    abstract val eventTimeGray: Color
    abstract val timeSelectorBoxGray: Color
    abstract val loginButtonLightGray: Color
    abstract val aiMessageLightGray: Color

    data class Light(
        override val primary: Color = Color(0xFFD0E4FF), // голубой для фона
        override val secondary: Color = Color(0xFF3C65A6), // темно-синий для шрифтов
        override val thirdly: Color = Color(0xFF333333), // серый
        override val disabled: Color = Color(0xFFC3D3E9),
        override val disabledContent: Color = Color(0xFF7792BE),
        override val success: Color = Color(0xFF34C759),
        override val error: Color = Color(0xFFFF6B6B),
        override val border: Color = Color(0xFFFFFFFF),
        override val white: Color = Color(0xFFFFFFFF),
        override val black: Color = Color(0xFF000000),
        override val red: Color = Color(0xFFFF0000),
        override val gray: Color = Color(0xFF959697),
        override val darkGray: Color = Color(0xFF474747),
        override val green: Color = Color(0xFF76BC1D),
        override val transparent: Color = Color(0x00000000),
        override val button: Color = Color(0xFFCFE3FF),
        override val selectedButton: Color = Color(0xFF94C1FF),
        override val containerBackground: Color = Color(0xFFF2F7FF),
        override val toothStroke: Color = Color(0xFFB1BDCC),
        override val toothSelected: Color = Color(0xFFCFE3FF),
        override val toothProblematic: Color = Color(0xFFFFF697),
        override val toothMissing: Color = Color(0xFF787878),
        override val lightGray: Color = Color(0xFF959697),
        override val bottomNavBarBackground: Color = Color(0xFFEDF4FF),
        override val eventBlue: Color = Color(0xFF0265DC),
        override val eventRed: Color = Color(0xFFFF025D),
        override val eventLightBlue: Color = Color(0xFF689BFE),
        override val eventOrange: Color = Color(0xFFFF9A02),
        override val eventTiffany: Color = Color(0xFF07D7D7),
        override val weekDaysGray: Color = Color(0xFF292929),
        override val eventTimeGray: Color = Color(0xFF666666),
        override val timeSelectorBoxGray: Color = Color(0xFFE3E3E3),
        override val loginButtonLightGray: Color = Color(0xFFD3D3D3),
        override val aiMessageLightGray: Color = Color(0xFFF2F2F7),
    ) : Colors()

    data class Dark(
        override val primary: Color = Color(0xFFD0E4FF),
        override val secondary: Color = Color(0xFF3C65A6),
        override val thirdly: Color = Color(0xFF333333),
        override val disabled: Color = Color(0xFFC3D3E9),
        override val disabledContent: Color = Color(0xFF7792BE),
        override val success: Color = Color(0xFF34C759),
        override val error: Color = Color(0xFFFF6B6B),
        override val border: Color = Color(0xFFFFFFFF),
        override val white: Color = Color(0xFFFFFFFF),
        override val black: Color = Color(0xFF000000),
        override val red: Color = Color(0xFFFF0000),
        override val gray: Color = Color(0xFF959697),
        override val darkGray: Color = Color(0xFF474747),
        override val green: Color = Color(0xFF76BC1D),
        override val transparent: Color = Color(0x00000000),
        override val button: Color = Color(0xFFCFE3FF),
        override val selectedButton: Color = Color(0xFF94C1FF),
        override val containerBackground: Color = Color(0xFFF2F7FF),
        override val toothStroke: Color = Color(0xFFB1BDCC),
        override val toothSelected: Color = Color(0xFFCFE3FF),
        override val toothProblematic: Color = Color(0xFFFFF697),
        override val toothMissing: Color = Color(0xFF787878),
        override val lightGray: Color = Color(0xFF959697),
        override val bottomNavBarBackground: Color = Color(0xFFEDF4FF),
        override val eventBlue: Color = Color(0xFF0265DC),
        override val eventRed: Color = Color(0xFFFF025D),
        override val eventLightBlue: Color = Color(0xFF689BFE),
        override val eventOrange: Color = Color(0xFFFF9A02),
        override val eventTiffany: Color = Color(0xFF07D7D7),
        override val weekDaysGray: Color = Color(0xFF292929),
        override val eventTimeGray: Color = Color(0xFF666666),
        override val timeSelectorBoxGray: Color = Color(0xFFE3E3E3),
        override val loginButtonLightGray: Color = Color(0xFFD3D3D3),
        override val aiMessageLightGray: Color = Color(0xFFF2F2F7),
    ) : Colors()
}

fun Color.invert() = Color(1f - red, 1f - green, 1f - blue, alpha)

fun String.toColorInt(): Int {
    val hex = this.removePrefix("#")
    return when (hex.length) {
        6 -> 0xFF000000.toInt() or hex.toInt(16)
        8 -> hex.toLong(16).toInt()
        else -> throw IllegalArgumentException("Неверный формат цвета: $this")
    }
}
