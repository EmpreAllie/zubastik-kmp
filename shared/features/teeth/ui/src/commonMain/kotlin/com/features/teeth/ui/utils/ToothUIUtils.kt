package com.features.teeth.ui.utils

import com.features.teeth.domain.model.Tooth
import com.features.ui.Res
import com.features.ui.canine
import com.features.ui.incisor
import com.features.ui.molar
import com.features.ui.premolar
import com.features.ui.tooth
import com.features.ui.wisdomTooth
import org.jetbrains.compose.resources.StringResource

fun List<Tooth>.findToothById(id: Int?) = this.find { it.id == id }

fun Tooth.getToothTypeStringResource(): StringResource = when (this.id % 10) {
        1, 2 -> Res.string.incisor
        3 -> Res.string.canine
        4, 5 -> Res.string.premolar
        6, 7 -> Res.string.molar
        8 -> Res.string.wisdomTooth
        else -> Res.string.tooth
    }