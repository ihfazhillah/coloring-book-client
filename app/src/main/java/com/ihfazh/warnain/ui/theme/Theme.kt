package com.ihfazh.warnain.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = Color(0xFFFD6B8E),
    secondary = Color(0xFF4193B0)
)

private val LightColorPalette = lightColors(
    primary = Color(0xFFFD6B8E),
    secondary = Color(0xFF4193B0),
    onSecondary = Color.White,
    onBackground = Color(0xFF3D1A22),
    surface = Color(0xFFB8ACAF),

    /* Other default colors to override
    background = Color.White,
    onPrimary = Color.White,
    onSecondary = Color.Black,
    onBackground = Color.Black,
    onSurface = Color.Black,
    */
)

@Composable
fun WarnainTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}