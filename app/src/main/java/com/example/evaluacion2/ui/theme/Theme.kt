package com.example.evaluacion2.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Azul80,
    secondary = Grafito80,
    tertiary = Celeste80
)

private val LightColorScheme = lightColorScheme(
    primary = Azul40,
    secondary = Grafito40,
    tertiary = Celeste40,
    background = Color(0xFFF7F8FA),
    surface = Color(0xFFFFFFFF)
)

@Composable
fun Evaluacion2Theme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}