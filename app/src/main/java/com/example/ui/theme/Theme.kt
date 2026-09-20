package com.example.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorScheme =
  darkColorScheme(
    primary = VodafoneRed,
    onPrimary = Color.White,
    primaryContainer = VodafoneCrimson,
    onPrimaryContainer = Color.White,
    secondary = VodafoneHappyGold,
    onSecondary = Color.Black,
    tertiary = VodafoneHappyPink,
    background = DarkBackground,
    surface = DarkSurface,
    surfaceVariant = DarkSurfaceVariant,
    onBackground = Color.White,
    onSurface = Color.White,
    onSurfaceVariant = Color(0xFFCCCCCC),
  )

private val LightColorScheme =
  lightColorScheme(
    primary = VodafoneRed,
    onPrimary = Color.White,
    primaryContainer = VodafoneLightRed,
    onPrimaryContainer = VodafoneDarkRed,
    secondary = VodafoneHappyOrange,
    onSecondary = Color.White,
    tertiary = VodafoneHappyGold,
    background = VodafoneBackground,
    surface = VodafoneSurface,
    surfaceVariant = VodafoneSurfaceVariant,
    onBackground = VodafoneTextPrimary,
    onSurface = VodafoneTextPrimary,
    onSurfaceVariant = VodafoneTextSecondary,
  )

@Composable
fun MyApplicationTheme(
  darkTheme: Boolean = isSystemInDarkTheme(),
  dynamicColor: Boolean = false, // Preserve Vodafone brand styling
  content: @Composable () -> Unit,
) {
  val colorScheme = if (darkTheme) DarkColorScheme else LightColorScheme

  MaterialTheme(
    colorScheme = colorScheme,
    typography = Typography,
    content = content
  )
}

