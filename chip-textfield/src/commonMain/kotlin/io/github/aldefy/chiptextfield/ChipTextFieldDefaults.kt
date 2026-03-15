package io.github.aldefy.chiptextfield

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.unit.dp

/**
 * Default values for [ChipTextField].
 */
object ChipTextFieldDefaults {

    /**
     * Default delimiters that trigger chip creation.
     * Comma and newline (Enter).
     */
    val delimiters: Set<Char> = setOf(',', '\n')

    /**
     * Default chip shape.
     */
    val shape: Shape = RoundedCornerShape(8.dp)

    /**
     * Creates default [ChipTextFieldColors] that adapt to light/dark theme.
     * Override individual colors to match your design system.
     */
    @Composable
    fun colors(
        chipBackgroundColor: Color = if (isSystemInDarkTheme()) Color(0xFF4A4458) else Color(0xFFE8DEF8),
        chipTextColor: Color = if (isSystemInDarkTheme()) Color(0xFFE8DEF8) else Color(0xFF1D1B20),
        chipBorderColor: Color = if (isSystemInDarkTheme()) Color(0xFF938F99) else Color(0xFF79747E),
        cursorColor: Color = if (isSystemInDarkTheme()) Color(0xFFD0BCFF) else Color(0xFF6750A4),
        textColor: Color = if (isSystemInDarkTheme()) Color(0xFFE6E1E5) else Color(0xFF1D1B20),
        placeholderColor: Color = if (isSystemInDarkTheme()) Color(0xFFCAC4D0) else Color(0xFF49454F),
        containerColor: Color = Color.Transparent,
        disabledChipBackgroundColor: Color = if (isSystemInDarkTheme()) Color(0x1FE6E1E5) else Color(0x1F1D1B20),
        disabledChipTextColor: Color = if (isSystemInDarkTheme()) Color(0x61E6E1E5) else Color(0x611D1B20),
    ): ChipTextFieldColors = ChipTextFieldColors(
        chipBackgroundColor = chipBackgroundColor,
        chipTextColor = chipTextColor,
        chipBorderColor = chipBorderColor,
        cursorColor = cursorColor,
        textColor = textColor,
        placeholderColor = placeholderColor,
        containerColor = containerColor,
        disabledChipBackgroundColor = disabledChipBackgroundColor,
        disabledChipTextColor = disabledChipTextColor,
    )
}
