package com.dev.briefing.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalView
import android.app.Activity
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.core.view.WindowCompat

@Composable
fun BriefingTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    // Dynamic color is available on Android 12+
    dynamicColor: Boolean = true,
    content: @Composable () -> Unit
) {

    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = localBriefingColor.PrimaryBlue.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }
    CompositionLocalProvider(
        LocalBriefingTypo provides localBriefingTypo,
        LocalBriefingColor provides localBriefingColor
    ) {
        MaterialTheme(
            content = content
        )
    }
}
object BriefingTheme {
    val typography: BriefingTypo
        @Composable
        get() = LocalBriefingTypo.current
    val color: BriefingColor
        @Composable
        get() = LocalBriefingColor.current
}
