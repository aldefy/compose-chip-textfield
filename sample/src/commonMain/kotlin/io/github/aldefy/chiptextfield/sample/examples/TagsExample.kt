package io.github.aldefy.chiptextfield.sample.examples

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.github.aldefy.chiptextfield.ChipTextField
import io.github.aldefy.chiptextfield.rememberChipTextFieldState

@Composable
fun TagsExample() {
    val state = rememberChipTextFieldState(
        initialChips = listOf("#kotlin", "#compose"),
    )

    ChipTextField(
        state = state,
        onCreateChip = { text ->
            val tag = if (text.startsWith("#")) text else "#$text"
            tag.takeIf { it.length > 1 }
        },
        maxChips = 5,
        placeholder = {
            Text(
                text = "Add tags (max 5)...",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
    )
}
