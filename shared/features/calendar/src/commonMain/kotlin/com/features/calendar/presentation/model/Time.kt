package com.features.calendar.presentation.model

typealias Time = Pair<Int, Int>

infix fun Int.andMinute(minute: Int): Time = this to minute

val Time.hour: Int get() = first
val Time.minute: Int get() = second

fun Time.format(): String = "${hour.toString().padStart(2, '0')}:${minute.toString().padStart(2, '0')}"
