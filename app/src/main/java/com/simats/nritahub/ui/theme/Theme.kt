package com.simats.nritahub.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable

private val DarkColorScheme = darkColorScheme(
    primary = DeepPlum,
    secondary = MediumMaroonText,
    tertiary = SoftLavenderBadge,
    background = DarkMaroonText,
    surface = DarkMaroonText
)

private val LightColorScheme = lightColorScheme(
    primary = DeepPlum,
    secondary = MediumMaroonText,
    tertiary = SoftLavenderBadge,
    background = CreamBackground,
    surface = CreamBackground,
    onPrimary = CardBackground,
    onBackground = DarkMaroonText,
    onSurface = DarkMaroonText
)

@Composable
fun NritahubTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

    MaterialTheme(
        colorScheme = colorScheme,
        typography = Typography,
        content = content
    )
}
