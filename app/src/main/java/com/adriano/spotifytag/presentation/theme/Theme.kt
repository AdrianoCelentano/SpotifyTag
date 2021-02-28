package com.adriano.spotifytag.presentation.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color

private val DarkColorPalette = darkColors(
    primary = spotifyGreen,
    onPrimary = Color.Black,
    primaryVariant = Color.Green,
    secondary = Color.Black,
    background = Color.Black
)

private val LightColorPalette = lightColors(
    primary = spotifyGreen,
    onPrimary = Color.Black,
    primaryVariant = Color.Green,
    secondary = Color.Black,
    background = Color.White

    /* Other default colors to override
background = Color.White,
surface = Color.White,
onSecondary = Color.Black,
onBackground = Color.Black,
onSurface = Color.Black,
*/
)

@Composable
fun SpotifyTagTheme(darkTheme: Boolean = isSystemInDarkTheme(), content: @Composable() () -> Unit) {
    val colors = if (darkTheme) {
        DarkColorPalette
    } else {
        LightColorPalette
    }

    MaterialTheme(
        colors = colors,
        typography = typography,
        shapes = shapes,
        content = content
    )
}
