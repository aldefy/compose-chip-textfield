---
title: Quick Start
description: Create your first chip input in 5 lines
---

## Basic usage

```kotlin
import io.github.aldefy.chiptextfield.ChipTextField
import io.github.aldefy.chiptextfield.rememberChipTextFieldState

@Composable
fun MyScreen() {
    val state = rememberChipTextFieldState<String>()

    ChipTextField(
        state = state,
        onCreateChip = { text -> text.ifBlank { null } },
        placeholder = {
            Text("Type and press Enter...")
        },
    )
}
```

That's it. Type text, press Enter (or type a delimiter like `,`), and a chip appears. Press backspace on an empty field to remove the last chip.

## With initial chips

```kotlin
val state = rememberChipTextFieldState(
    initialChips = listOf("Kotlin", "Compose", "Multiplatform")
)
```

## Custom data types

ChipTextField is generic — use any type, not just strings:

```kotlin
data class Contact(val name: String, val email: String)

val state = rememberChipTextFieldState<Contact>()

ChipTextField(
    state = state,
    onCreateChip = { text ->
        // Parse or validate input, return null to reject
        Contact(name = text, email = "$text@example.com")
    },
    chipLabel = { it.name }, // display name on chip
)
```

## Reading chip state

```kotlin
val state = rememberChipTextFieldState<String>()

// Access chips reactively
val chipCount = state.chips.size

// Programmatic operations
state.addChip("New chip")
state.removeChip("Old chip")
state.clearChips()
```

## Next steps

- [Chip Customization](/compose-chip-textfield/guide/chip-customization/) — icons, colors, full custom rendering
- [Suggestions](/compose-chip-textfield/guide/suggestions/) — dropdown suggestions as users type
- [Validation & Limits](/compose-chip-textfield/guide/validation/) — email validation, max chips, delimiters
