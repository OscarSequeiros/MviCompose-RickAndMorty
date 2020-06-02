package com.example.mvicompose.ui

import androidx.ui.graphics.Color
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette

val lightThemeColors = lightColorPalette(
    primary = Color(98, 0, 238),
    primaryVariant = Color(55, 0, 179),
    onPrimary = Color.White,
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