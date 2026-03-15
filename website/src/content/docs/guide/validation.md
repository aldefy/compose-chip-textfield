---
title: Validation & Limits
description: Control what gets created as a chip
---

## Input validation

Return `null` from `onCreateChip` to reject input:

```kotlin
val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

ChipTextField(
    state = state,
    onCreateChip = { text ->
        text.trim().takeIf { emailRegex.matches(it) }
    },
)
```

When `onCreateChip` returns `null`, the text stays in the input field — the user can edit and retry.

## Max chips

Limit the number of chips. The input field hides when the limit is reached:

```kotlin
ChipTextField(
    state = state,
    onCreateChip = { it },
    maxChips = 5,
)
```

## Custom delimiters

By default, chips are created on `,` and `\n` (Enter). Customize with `delimiters`:

```kotlin
ChipTextField(
    state = state,
    onCreateChip = { it },
    delimiters = setOf(',', '\n', ' ', ';'),
)
```

This is useful for email inputs where space should also create a chip.

## Read-only mode

Display chips without allowing edits:

```kotlin
ChipTextField(
    state = state,
    onCreateChip = { it },
    readOnly = true, // chips visible but not editable
)
```

## Disabled state

Fully disable the field:

```kotlin
ChipTextField(
    state = state,
    onCreateChip = { it },
    enabled = false, // grayed out, no interaction
)
```

## Removal callback

React when a chip is removed (e.g., for analytics or syncing state):

```kotlin
ChipTextField(
    state = state,
    onCreateChip = { it },
    onChipRemoved = { chip ->
        println("Removed: $chip")
    },
)
```
