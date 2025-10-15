package com.features.ui.theme

import androidx.compose.ui.graphics.Color

sealed class Colors {
    abstract val primary: Color
    abstract val secondary: Color
    abstract val thirdly: Color
    abstract val error: Color
    abstract val border: Color
    abstract val white: Color
    abstract val black: Color
    abstract val red: Color
    abstract val gray: Color
    abstract val green: Color
    abstract val transparent: Color
    abstract val buttonYellow: Color

    data class Light(
        override val primary: Color = Color(0xFFC4D3FF), // фиолетово-голубой для фона
        override val secondary: Color = Color(0xFF14316F), // темно-синий для шрифтов
        override val thirdly: Color = Color(0xFF333333), // серый
        override val error: Color = Color(0xFFFB4545),
        override val border: Color = Color(0xFFFFFFFF),
        override val white: Color = Color(0xFFFFFFFF),
        override val black: Color = Color(0xFF000000),
        override val red: Color = Color(0xFFFF0000),
        override val gray: Color = Color(0xFFF2F0F0),
        override val green: Color = Color(0xFF76BC1D),
        override val transparent: Color = Color(0x00000000),
        override val buttonYellow: Color = Color(0xFFFFCA56), // желтый для кнопок
    ) : Colors()

    data class Dark(
        override val primary: Color = Color(0xFFC4D3FF),
        override val secondary: Color = Color(0xFF14316F),
        override val thirdly: Color = Color(0xFF333333),
        override val error: Color = Color(0xFFFB4545),
        override val border: Color = Color(0xFFFFFFFF),
        override val white: Color = Color(0xFFFFFFFF),
        override val black: Color = Color(0xFF000000),
        override val red: Color = Color(0xFFFF0000),
        override val gray: Color = Color(0xFFF2F0F0),
        override val green: Color = Color(0xFF76BC1D),
        override val transparent: Color = Color(0x00000000),
        override val buttonYellow: Color = Color(0xFFFFCA56),
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
