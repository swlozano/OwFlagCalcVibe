package com.imainfun.owflagcalc.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme = darkColorScheme(
    primary = Color(0xFFE85A75),
    secondary = Color(0xFFE85A75),
    tertiary = Color(0xFFE85A75)
)

private val LightColorScheme = lightColorScheme(
    primary = Color(0xFFE85A75),
    secondary = Color(0xFFE85A75),
    tertiary = Color(0xFFE85A75)
)

@Composable
fun OwFlagCalcTripClaudeTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    content: @Composable () -> Unit
) {
    val colorScheme = when {
        darkTheme -> DarkColorScheme
        else -> LightColorScheme
    }

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}