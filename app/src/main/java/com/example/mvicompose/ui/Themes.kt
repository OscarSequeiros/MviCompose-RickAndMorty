package com.example.mvicompose.ui

import androidx.ui.graphics.Color
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette

val lightThemeColors = lightColorPalette(
    primary = Color(0xFF0277bd),
    primaryVariant = Color(0xFF004c8c),
    onPrimary = Color.White,
    secondary = Color(0xFFFFDF00),
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFD00036),
    onError = Color.White
)

val darkThemeColors = darkColorPalette(
    primary = Color(240, 233, 233),
    primaryVariant = Color(237, 245, 243),
    onPrimary = Color(212, 212, 195),
    secondary = Color(0, 150, 136),
    onSecondary = Color.Black,
    background = Color.White,
    onBackground = Color.Black,
    surface = Color.White,
    onSurface = Color.Black,
    error = Color(0xFFD00036),
    onError = Color.White
)