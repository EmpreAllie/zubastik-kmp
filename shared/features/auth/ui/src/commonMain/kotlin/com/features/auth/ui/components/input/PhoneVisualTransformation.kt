package com.features.auth.ui.components.input

import androidx.compose.ui.text.AnnotatedString
import androidx.compose.ui.text.input.OffsetMapping
import androidx.compose.ui.text.input.TransformedText
import androidx.compose.ui.text.input.VisualTransformation

class PhoneVisualTransformation : VisualTransformation {
    override fun filter(text: AnnotatedString): TransformedText {

        val digits = text.text.filter { it.isDigit() }

        val formattedNumber = buildString {
            if (digits.isNotEmpty())
                append("(")
            digits.take(3).forEach { append(it) }

            if (digits.length > 3)
                append(") ")
            digits.drop(3).take(3).forEach { append(it) }

            if (digits.length > 6)
                append("-")
            digits.drop(6).take(2).forEach { append(it) }

            if (digits.length > 8)
                append("-")
            digits.drop(8).take(2).forEach { append(it) }
        }

        val offsetMapping = object : OffsetMapping {
            override fun originalToTransformed(offset: Int): Int {
                if (offset <= 0)
                    return offset
                if (offset <= 3)
                    return offset + 1
                if (offset <= 6)
                    return offset + 3
                if (offset <= 8)
                    return offset + 4
                if (offset <= 10)
                    return offset + 5
                return 15
            }

            override fun transformedToOriginal(offset: Int): Int {
                if (offset <= 1)
                    return offset
                if (offset <= 5)
                    return offset - 1
                if (offset <= 9)
                    return offset - 3
                if (offset <= 12)
                    return offset - 4
                if (offset <= 15)
                    return offset - 5
                return 10
            }
        }

        return TransformedText(
            text = AnnotatedString(formattedNumber),
            offsetMapping = offsetMapping
        )
    }
}