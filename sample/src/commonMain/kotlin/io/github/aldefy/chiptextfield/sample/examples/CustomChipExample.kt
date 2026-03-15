package io.github.aldefy.chiptextfield.sample.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.unit.dp
import io.github.aldefy.chiptextfield.ChipTextField
import io.github.aldefy.chiptextfield.rememberChipTextFieldState

data class Priority(val label: String, val level: Int)

@Composable
fun CustomChipExample() {
    val state = rememberChipTextFieldState<Priority>()

    ChipTextField(
        state = state,
        onCreateChip = { text ->
            when {
                text.startsWith("!") -> Priority(text.removePrefix("!"), level = 3)
                text.startsWith("*") -> Priority(text.removePrefix("*"), level = 2)
                else -> Priority(text, level = 1)
            }
        },
        chipContent = { chip, onRemove ->
            val color = when (chip.level) {
                3 -> MaterialTheme.colorScheme.errorContainer
                2 -> MaterialTheme.colorScheme.tertiaryContainer
                else -> MaterialTheme.colorScheme.secondaryContainer
            }
            val textColor = when (chip.level) {
                3 -> MaterialTheme.colorScheme.onErrorContainer
                2 -> MaterialTheme.colorScheme.onTertiaryContainer
                else -> MaterialTheme.colorScheme.onSecondaryContainer
            }

            Row(
                modifier = Modifier
                    .clip(RoundedCornerShape(16.dp))
                    .background(color)
                    .padding(horizontal = 12.dp, vertical = 6.dp),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Text(
                    text = chip.label,
                    color = textColor,
                    style = MaterialTheme.typography.bodySmall,
                )
                Icon(
                    imageVector = Icons.Default.Close,
                    contentDescription = "Remove ${chip.label}",
                    tint = textColor,
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(14.dp)
                        .clickable(onClick = onRemove),
                )
            }
        },
        placeholder = {
            Text(
                text = "Prefix ! or * for priority...",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
    )
}
