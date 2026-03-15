package io.github.aldefy.chiptextfield.sample.examples

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Email
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import io.github.aldefy.chiptextfield.ChipTextField
import io.github.aldefy.chiptextfield.rememberChipTextFieldState

private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

@Composable
fun EmailInputExample() {
    val state = rememberChipTextFieldState<String>()

    ChipTextField(
        state = state,
        onCreateChip = { text ->
            text.trim().takeIf { emailRegex.matches(it) }
        },
        placeholder = {
            Text(
                text = "Add email addresses...",
                color = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        leadingIcon = {
            Icon(
                imageVector = Icons.Default.Email,
                contentDescription = "Email",
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        },
        delimiters = setOf(',', '\n', ' '),
    )
}
