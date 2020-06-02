package com.example.mvicompose.ui

import androidx.ui.graphics.Color
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette
import androidx.ui.text.font.FontStyle
import androidx.ui.text.font.FontWeight
import androidx.ui.text.font.ResourceFont
import androidx.ui.text.font.fontFamily
import androidx.ui.material.Typography
import com.example.mvicompose.R

val lightThemeColors = lightColorPalette(
    primary = Color(98, 0, 238),
    primaryVariant = Color(55, 0, 179),
    onPrimary = Color(18, 7, 9),
    secondary = Color(0, 150, 136),
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(150, 0, 20),
    onError = Color.White
)

val darkThemeColors = darkColorPalette(
    primary = Color(98, 0, 238),
    primaryVariant = Color(55, 0, 179),
    onPrimary = Color(212, 212, 195),
    secondary = Color(0, 150, 136),
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(150, 0, 20),
    onError = Color.White
)

val appFontFamily = fontFamily(
    fonts = listOf(
        ResourceFont(
            resId = R.font.lato_bold,
            weight = FontWeight.Medium
        ),
        ResourceFont(
            resId = R.font.lato_italic,
            weight = FontWeight.W300,
            style = FontStyle.Italic
        ),
        ResourceFont(
            resId = R.font.lato_regular,
            weight = FontWeight.W400,
            style = FontStyle.Normal
        )
    )
)

private val defaultTypography = Typography()

val appTypography = Typography(
    h1 = defaultTypography.h1.copy(fontFamily = appFontFamily),
    h2 = defaultTypography.h2.copy(fontFamily = appFontFamily),
    h3 = defaultTypography.h3.copy(fontFamily = appFontFamily),
    h4 = defaultTypography.h4.copy(fontFamily = appFontFamily),
    h5 = defaultTypography.h5.copy(fontFamily = appFontFamily),
    h6 = defaultTypography.h6.copy(fontFamily = appFontFamily),
    subtitle1 = defaultTypography.subtitle1.copy(fontFamily = appFontFamily),
    subtitle2 = defaultTypography.subtitle2.copy(fontFamily = appFontFamily),
    body1 = defaultTypography.body1.copy(fontFamily = appFontFamily),
    body2 = defaultTypography.body2.copy(fontFamily = appFontFamily),
    button = defaultTypography.button.copy(fontFamily = appFontFamily),
    caption = defaultTypography.caption.copy(fontFamily = appFontFamily),
    overline = defaultTypography.overline.copy(fontFamily = appFontFamily)
)