package com.dev.briefing.presentation.theme

import androidx.compose.material3.Typography
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.dev.briefing.R

// Set of Material typography styles to start with
val ProductSans = FontFamily(
    Font(R.font.productsansbold, FontWeight.Bold),
    Font(R.font.productsansregular, FontWeight.Normal),
)

@Immutable
data class BriefingTypo(
    val bold30: TextStyle,
    val regular30: TextStyle,
    val bold20: TextStyle,
    val regular20: TextStyle,
    val bold17: TextStyle,
    val regular17: TextStyle,
    val bold15: TextStyle,
    val regular15: TextStyle,
    val bold13: TextStyle,
    val regular13: TextStyle,
)

val localBriefingTypo =
    BriefingTypo(
        bold30 = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Medium,
            fontSize = 30.sp,
            lineHeight = 36.39.sp
        ),
        regular30 = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Normal,
            fontSize = 30.sp,
            lineHeight = 36.39.sp
        ),
        bold20 = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 24.39.sp
        ),
        regular20 = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 24.39.sp
        ),
        bold17 = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            lineHeight = 20.69.sp
        ),
        regular17 = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            lineHeight = 20.69.sp
        ),
        bold15 = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            lineHeight = 18.39.sp
        ),
        regular15 = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 18.39.sp
        ),
        bold13 = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            lineHeight = 15.99.sp
        ),
        regular13 = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = 15.99.sp
        ),
    )
val LocalBriefingTypo = staticCompositionLocalOf {
    BriefingTypo(
        bold30 = TextStyle.Default,
        regular30 = TextStyle.Default,
        bold20 = TextStyle.Default,
        regular20 = TextStyle.Default,
        bold17 = TextStyle.Default,
        regular17 = TextStyle.Default,
        bold15 = TextStyle.Default,
        regular15 = TextStyle.Default,
        bold13 = TextStyle.Default,
        regular13 = TextStyle.Default,
    )
}
