package com.dev.briefing.presentation.theme

import androidx.compose.material3.Typography
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
val Typography = Typography(
    titleLarge = TextStyle(
        fontFamily = ProductSans,
        fontWeight = FontWeight.Bold,
        color = MainPrimary,
        fontSize = 30.sp,
        lineHeight = 36.sp
    ),
    titleMedium= TextStyle(
        fontFamily = ProductSans,
        fontWeight = FontWeight.Bold,
        color = MainPrimary,
        fontSize = 25.sp,
        lineHeight = 30.sp
    ),
    titleSmall= TextStyle(
        fontFamily = ProductSans,
        fontWeight = FontWeight.Bold,
        color = MainPrimary,
        fontSize = 20.sp,
        lineHeight = 20.sp
    ),
    headlineLarge= TextStyle(
        fontFamily = ProductSans,
        fontWeight = FontWeight.Bold,
        color = MainPrimary,
        fontSize = 17.sp,
        lineHeight = 18.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = ProductSans,
        fontWeight = FontWeight.Normal,
        color = MainPrimary3,
        fontSize = 13.sp,
        lineHeight = 22.sp
    ),
    labelLarge = TextStyle(
        fontFamily = ProductSans,
        fontWeight = FontWeight.Normal,
        color = MainPrimary3,
        fontSize = 14.sp,
        lineHeight = 16.sp
    ),

    labelMedium = TextStyle(
        fontFamily = ProductSans,
        fontWeight = FontWeight.Normal,
        color = MainPrimary3,
        fontSize = 11.sp,
        lineHeight = 13.sp
    ),
    labelSmall= TextStyle(
        fontFamily = ProductSans,
        fontWeight = FontWeight.Bold,
        color = MainPrimary3,
        fontSize = 10.sp,
        lineHeight = 12.sp
    )

)