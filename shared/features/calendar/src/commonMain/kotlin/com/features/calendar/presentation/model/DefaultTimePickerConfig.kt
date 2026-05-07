package com.features.calendar.presentation.model

// Из этого класса берётся время по умолчанию, которое выставляется в TimePicker
enum class DefaultTimePickerConfig(
    val hour: Int,
    val minute: Int,
) {
    CALENDAR_DEFAULT(
        hour = 12,
        minute = 0,
    ),
}
