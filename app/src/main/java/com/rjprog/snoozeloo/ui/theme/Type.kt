package com.rjprog.snoozeloo.ui.theme

import androidx.compose.material3.Typography
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.sp
import com.rjprog.snoozeloo.R

// Set of Material typography styles to start with
val Typography = Typography(
    bodyLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat_medium) ),
        fontWeight = FontWeight(600),
        fontSize = 16.sp,
        lineHeight = 19.5.sp,
        letterSpacing = 0.sp
    ),
    bodyMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat_medium) ),
        fontWeight = FontWeight(500),
        fontSize = 14.sp,
        lineHeight = 17.07.sp,
        letterSpacing = 0.sp
    ),
    titleLarge = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat_medium) ),
        fontWeight = FontWeight(500),
        fontSize = 82.sp,
        lineHeight = 99.96.sp,
        letterSpacing = 0.5.sp
    ),
    titleMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat_medium) ),
        fontWeight = FontWeight(500),
        fontSize = 52.sp,
        lineHeight = 63.39.sp,
        letterSpacing = 0.5.sp
    ),
    titleSmall = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat_medium) ),
        fontWeight = FontWeight(500),
        fontSize = 24.sp,
        lineHeight = 29.26.sp,
        letterSpacing = 0.0.sp
    ),
    headlineMedium = TextStyle(
        fontFamily = FontFamily(Font(R.font.montserrat_medium) ),
        fontWeight = FontWeight(500),
        fontSize = 42.sp,
        lineHeight = 51.2.sp,
        letterSpacing = 0.0.sp
    ),
    labelSmall = TextStyle(
        fontFamily = FontFamily.Default,
        fontWeight = FontWeight.Medium,
        fontSize = 11.sp,
        lineHeight = 16.sp,
        letterSpacing = 0.5.sp
    )

)