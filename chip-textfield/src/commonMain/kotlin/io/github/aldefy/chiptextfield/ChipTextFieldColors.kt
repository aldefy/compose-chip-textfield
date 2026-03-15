package io.github.aldefy.chiptextfield

import androidx.compose.runtime.Immutable
import androidx.compose.ui.graphics.Color

/**
 * Color configuration for [ChipTextField] and its default chip rendering.
 */
@Immutable
data class ChipTextFieldColors(
    val chipBackgroundColor: Color,
    val chipTextColor: Color,
    val chipBorderColor: Color,
    val cursorColor: Color,
    val textColor: Color,
    val placeholderColor: Color,
    val containerColor: Color,
    val disabledChipBackgroundColor: Color,
    val disabledChipTextColor: Color,
)
