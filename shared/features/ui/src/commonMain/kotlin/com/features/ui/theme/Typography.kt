package com.features.ui.theme

import androidx.compose.runtime.Immutable
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.unit.em
import androidx.compose.ui.unit.sp

@Immutable
data class Typography(private val font: FontFamily) {
    val main: TitleTypography =
        TitleTypography(font)
    val dialog: DialogTypography =
        DialogTypography(font)
    val drawer: DrawerContentTypography =
        DrawerContentTypography(font)
    val profile: ProfileTypography =
        ProfileTypography(font)
    val settings: SettingsTypography =
        SettingsTypography(font)
    val onboarding: OnboardingTypography =
        OnboardingTypography(font)
    val onboardingBrushingDialog: OnboardingBrushingDialogTypography =
        OnboardingBrushingDialogTypography(font)
    val bonus: BonusTypography =
        BonusTypography(font)
    val route: RouteTypography =
        RouteTypography(font)
    val auth: AuthTypography =
        AuthTypography(font)
    val message: MessageTypography =
        MessageTypography(font)


}

@Immutable
data class TitleTypography(private val font: FontFamily) {
    val title: TextStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
        letterSpacing = 0.1.em
    ).preciseLineHeight()

    val main: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val buttonText: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    )

    val secondButtonText: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
    )

    val inputText: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val disabledTextField: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val hintText: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val lowText: TextStyle = TextStyle(
        fontSize = 12.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val underline: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
        textDecoration = TextDecoration.Underline
    ).preciseLineHeight()

    val coin: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 12.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    ).preciseLineHeight()
}

@Immutable
data class DialogTypography(private val font: FontFamily) {
    val title: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600
    ).preciseLineHeight()

    val main: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400
    ).preciseLineHeight()

    val buttonText: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
    )
}

@Immutable
data class DrawerContentTypography(private val font: FontFamily) {
    val name: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    )

    val coin: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    )

    val button: TextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    )

    val exit: TextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    )
}

@Immutable
data class ProfileTypography(private val font: FontFamily) {
    val title: TextStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600
    ).preciseLineHeight()

    val coin: TextStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600
    ).preciseLineHeight()

    val cardTitle: TextStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 22.sp,
        fontWeight = FontWeight.W600,
    )

    val cardDescription: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
    )
}

@Immutable
data class SettingsTypography(private val font: FontFamily) {
    val title: TextStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600
    ).preciseLineHeight()

    val titleGroup: TextStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
    )
}

@Immutable
data class OnboardingTypography(private val font: FontFamily) {
    val upperStateRow: TextStyle = TextStyle(
        fontSize = 22.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    ).preciseLineHeight()

    val chatContainerContent: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 25.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    )

    val buttonText: TextStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 30.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    )

    val alreadyFamiliar: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    )

}

@Immutable
data class OnboardingBrushingDialogTypography(private val font: FontFamily) {
    val brushingFrequency: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 25.sp,
        fontFamily = font,
        fontWeight = FontWeight.Bold
    )

    val brushingFrequencyHint: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 18.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    )

    val brushingFrequencyListItem: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 19.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    )
}

@Immutable
data class BonusTypography(private val font: FontFamily) {
    val title: TextStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 24.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600
    ).preciseLineHeight()

    val company: TextStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    )

    val description: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
    )

    val route: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
    )

    val slider: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
    )

    val expiry: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
    )

    val coin: TextStyle = TextStyle(
        fontSize = 28.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    )

    val button: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    )

    val buttonDescription: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    )
}

@Immutable
data class RouteTypography(private val font: FontFamily) {
    val creator: TextStyle = TextStyle(
        fontSize = 12.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
    )

    val checked: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    )

    val rate: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
    )

    val title: TextStyle = TextStyle(
        fontSize = 20.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W600,
    )

    val description: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 22.sp,
        fontFamily = font,
        fontWeight = FontWeight.W400,
    )
}

@Immutable
data class AuthTypography(private val font: FontFamily) {
    val title: TextStyle = TextStyle(
        fontSize = 36.sp,
        fontFamily = font,
        fontWeight = FontWeight.SemiBold
    )

    val countryCode: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    )

    val secondary: TextStyle = TextStyle(
        fontSize = 18.sp,
        lineHeight = 34.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    )

    val phoneNumberOnCodeScreen: TextStyle = TextStyle(
        fontSize = 24.sp,
        lineHeight = 34.sp,
        fontFamily = font,
        fontWeight = FontWeight.SemiBold
    )

    val sendCodeAgain: TextStyle = TextStyle(
        fontSize = 14.sp,
        lineHeight = 18.sp,
        fontFamily = font,
        fontWeight = FontWeight.Medium
    )

    val inputNumber: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 24.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    )

    val inputCode: TextStyle = TextStyle(
        fontSize = 22.sp,
        lineHeight = 28.sp,
        fontFamily = font,
        fontWeight = FontWeight.Bold
    )
}

@Immutable
data class MessageTypography(private val font: FontFamily) {
    val author: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.Bold
    )

    val text: TextStyle = TextStyle(
        fontSize = 16.sp,
        lineHeight = 20.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    )

    val time: TextStyle = TextStyle(
        fontSize = 8.sp,
        lineHeight = 10.sp,
        fontFamily = font,
        fontWeight = FontWeight.Normal
    )
}

expect fun TextStyle.preciseLineHeight(): TextStyle