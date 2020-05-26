package com.goofy.goober.ui.theme

import androidx.compose.Composable
import androidx.ui.foundation.isSystemInDarkTheme
import androidx.ui.graphics.Color
import androidx.ui.material.MaterialTheme
import androidx.ui.material.darkColorPalette
import androidx.ui.material.lightColorPalette

private val LightThemeColors = lightColorPalette(
    primary = Indigo,
    primaryVariant = MediumPurple,
    onPrimary = Color.White,
    secondary = Indigo,
    secondaryVariant = Color.White,
    onSecondary = BlueViolet,
    background = Color.White
)

private val DarkThemeColors = darkColorPalette(
    primary = Indigo,
    primaryVariant = BlueViolet,
    onPrimary = Color.White,
    secondary = DarkMagenta,
    onSecondary = Color.White,
    background = DarkBlueGrey
)

@Composable
fun PizzaAppTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable() () -> Unit
) {
    MaterialTheme(
        colors = DarkThemeColors,
//        colors = if (darkTheme) DarkThemeColors else LightThemeColors,
        content = content
    )
}
