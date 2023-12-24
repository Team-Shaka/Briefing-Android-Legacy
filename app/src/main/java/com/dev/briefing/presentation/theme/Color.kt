package com.dev.briefing.presentation.theme

import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color

@Immutable
data class BriefingColor(
    val PrimaryBlue: Color,
    val TextBlack: Color,
    val TextGray: Color,
    val TextRed: Color,
    val BackgrundGray:Color,
    val SeperatorGray: Color,
    val BackgroundWhite: Color,
)

val localBriefingColor =
    BriefingColor(
        PrimaryBlue = Color(0xFF306DAB),
        TextBlack = Color(0xFF000000),
        TextGray = Color(0x997C7C7C),
        TextRed = Color(0xFFFF0000),
        BackgrundGray = Color(0xFFF8F8F8),
        SeperatorGray = Color(0xFFDADADA),
        BackgroundWhite = Color(0xFFFFFFFF),
    )
val LocalBriefingColor = staticCompositionLocalOf {
    BriefingColor(
        PrimaryBlue = Color.Unspecified,
        TextBlack = Color.Unspecified,
        TextGray = Color.Unspecified,
        TextRed = Color.Unspecified,
        BackgrundGray = Color.Unspecified,
        SeperatorGray = Color.Unspecified,
        BackgroundWhite = Color.Unspecified,
    )
}

//legacy
val Black = Color(0xFF000000)
val GradientStart = Color(0xFF4686CD)
val GradientEnd = Color(0xFF134D80)
