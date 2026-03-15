package io.github.aldefy.chiptextfield

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * Scope for defining chip content using the slot pattern.
 * Provides access to chip state for conditional rendering.
 */
@Immutable
class ChipScope<T>(
    val chip: T,
    val enabled: Boolean,
    val colors: ChipTextFieldColors,
    val onRemove: () -> Unit,
)

/**
 * Default chip rendering using Foundation primitives only (no Material dependency).
 * Uses the slot pattern for leading icon, label, and trailing remove button.
 */
@Composable
internal fun <T> DefaultChipContent(
    chip: T,
    label: String,
    enabled: Boolean,
    colors: ChipTextFieldColors,
    leadingIcon: (@Composable ChipScope<T>.() -> Unit)?,
    trailingIcon: (@Composable ChipScope<T>.() -> Unit)?,
    onRemove: () -> Unit,
) {
    val chipShape = RoundedCornerShape(8.dp)
    val bgColor = if (enabled) colors.chipBackgroundColor else colors.disabledChipBackgroundColor
    val textColor = if (enabled) colors.chipTextColor else colors.disabledChipTextColor
    val hasLeadingIcon = leadingIcon != null
    val scope = ChipScope(chip, enabled, colors, onRemove)

    Row(
        modifier = Modifier
            .clip(chipShape)
            .background(bgColor)
            .border(width = 1.dp, color = colors.chipBorderColor, shape = chipShape)
            .padding(
                start = if (hasLeadingIcon) 4.dp else 12.dp,
                end = 8.dp,
                top = if (hasLeadingIcon) 4.dp else 6.dp,
                bottom = if (hasLeadingIcon) 4.dp else 6.dp,
            ),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        if (leadingIcon != null) {
            scope.leadingIcon()
            Spacer(modifier = Modifier.width(6.dp))
        }
        BasicText(
            text = label,
            style = TextStyle(
                color = textColor,
                fontSize = 14.sp,
            ),
        )
        if (enabled) {
            if (trailingIcon != null) {
                Spacer(modifier = Modifier.width(4.dp))
                scope.trailingIcon()
            } else {
                DefaultCloseButton(
                    color = textColor,
                    onRemove = onRemove,
                )
            }
        }
    }
}

@Composable
internal fun DefaultCloseButton(
    color: androidx.compose.ui.graphics.Color,
    onRemove: () -> Unit,
) {
    Canvas(
        modifier = Modifier
            .padding(start = 4.dp)
            .size(18.dp)
            .clickable(onClick = onRemove),
    ) {
        val padding = size.width * 0.25f
        drawLine(
            color = color,
            start = Offset(padding, padding),
            end = Offset(size.width - padding, size.height - padding),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round,
        )
        drawLine(
            color = color,
            start = Offset(size.width - padding, padding),
            end = Offset(padding, size.height - padding),
            strokeWidth = 2.dp.toPx(),
            cap = StrokeCap.Round,
        )
    }
}
