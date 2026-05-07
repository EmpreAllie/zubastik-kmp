package com.core.data.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlinx.datetime.todayIn
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

class DateTimeManager {
    @OptIn(ExperimentalTime::class)
    fun getCurrentTimeStamp(): String {
        val now = Clock.System.now()
        val localTime = now.toLocalDateTime(TimeZone.currentSystemDefault())

        val hours = localTime.hour.toString().padStart(2, '0')
        val minutes = localTime.minute.toString().padStart(2, '0')

        return "$hours:$minutes"
    }

    @OptIn(ExperimentalTime::class)
    fun getCurrentLocalDate(): LocalDate =
        Clock.System.todayIn(currentTimeZone).run {
            LocalDate(year, month, 1)
        }

    val currentTimeZone = TimeZone.currentSystemDefault()
}
