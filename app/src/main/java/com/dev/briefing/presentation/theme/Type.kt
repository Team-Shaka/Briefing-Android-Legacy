package com.dev.briefing.presentation.theme

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
    val TitleStyleBold: TextStyle,
    val TitleStyleRegular: TextStyle,
    val SubtitleStyleBold: TextStyle,
    val SubtitleStyleRegular: TextStyle,
    val ContextStyleBold: TextStyle,
    val ContextStyleRegular: TextStyle,
    val ContextStyleRegular25: TextStyle,
    val SmallcontextStyleBold: TextStyle,
    val SmallcontextStyleRegular: TextStyle,
    val DetailStyleBold: TextStyle,
    val DetailStyleRegular: TextStyle,
)

val localBriefingTypo =
    BriefingTypo(
        TitleStyleBold = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Medium,
            fontSize = 30.sp,
            lineHeight = 36.39.sp
        ),
        TitleStyleRegular = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Normal,
            fontSize = 30.sp,
            lineHeight = 36.39.sp
        ),
        SubtitleStyleBold = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Bold,
            fontSize = 20.sp,
            lineHeight = 24.39.sp
        ),
        SubtitleStyleRegular = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Normal,
            fontSize = 20.sp,
            lineHeight = 24.39.sp
        ),
        ContextStyleBold = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Bold,
            fontSize = 17.sp,
            lineHeight = 20.69.sp
        ),
        ContextStyleRegular25 = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            lineHeight = 25.sp
        ),
        ContextStyleRegular = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Normal,
            fontSize = 17.sp,
            lineHeight = 20.69.sp
        ),
        SmallcontextStyleBold = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Bold,
            fontSize = 15.sp,
            lineHeight = 18.39.sp
        ),
        SmallcontextStyleRegular = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Normal,
            fontSize = 15.sp,
            lineHeight = 18.39.sp
        ),
        DetailStyleBold = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Bold,
            fontSize = 13.sp,
            lineHeight = 15.99.sp
        ),
        DetailStyleRegular = TextStyle(
            color = Black,
            fontFamily = ProductSans,
            fontWeight = FontWeight.Normal,
            fontSize = 13.sp,
            lineHeight = 15.99.sp
        ),
    )
val LocalBriefingTypo = staticCompositionLocalOf {
    BriefingTypo(
        TitleStyleBold = TextStyle.Default,
        TitleStyleRegular = TextStyle.Default,
        SubtitleStyleBold = TextStyle.Default,
        SubtitleStyleRegular = TextStyle.Default,
        ContextStyleBold = TextStyle.Default,
        ContextStyleRegular = TextStyle.Default,
        ContextStyleRegular25 = TextStyle.Default,
        SmallcontextStyleBold = TextStyle.Default,
        SmallcontextStyleRegular = TextStyle.Default,
        DetailStyleBold = TextStyle.Default,
        DetailStyleRegular = TextStyle.Default,
    )
}
