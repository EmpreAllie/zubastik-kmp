package com.core.data.utils

import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object DateTimeManager {

    @OptIn(ExperimentalTime::class)
    public fun getCurrentTimeStamp(): String {
        val now = Clock.System.now()
        val localTime = now.toLocalDateTime(TimeZone.currentSystemDefault())

        val hours = localTime.hour.toString().padStart(2, '0')
        val minutes = localTime.minute.toString().padStart(2, '0')

        return "$hours:$minutes"
    }

}