package com.discarduel.game.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.darkColorScheme
import androidx.compose.runtime.Composable

private val FiveCardsColorScheme = darkColorScheme(
    primary = GoldAccent,
    onPrimary = TableGreenDark,
    secondary = TableGreenLight,
    onSecondary = CardWhite,
    background = TableGreenDark,
    onBackground = CardWhite,
    surface = TableGreen,
    onSurface = CardWhite,
    error = CardRed,
    onError = CardWhite
)

@Composable
fun FiveCardsTheme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = FiveCardsColorScheme,
        content = content
    )
}
