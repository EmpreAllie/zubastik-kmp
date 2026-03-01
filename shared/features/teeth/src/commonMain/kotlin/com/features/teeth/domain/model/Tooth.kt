package com.features.teeth.domain.model

data class Tooth(
    val id: Int,
    val type: ToothType,
    val status: ToothStatus,
    val note: String? = null
)
