package io.github.aldefy.chiptextfield.sample.examples

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.github.aldefy.chiptextfield.ChipTextField
import io.github.aldefy.chiptextfield.rememberChipTextFieldState

@Composable
fun BasicExample() {
    val state = rememberChipTextFieldState<String>()

    ChipTextField(
        state = state,
        onCreateChip = { text -> text.ifBlank { null } },
        placeholder = {
            Text(
                text = "Type and press Enter...",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
    )
}
