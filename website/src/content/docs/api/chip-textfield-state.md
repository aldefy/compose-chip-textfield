---
title: ChipTextFieldState
description: State holder for chips
---

## Definition

```kotlin
@Stable
class ChipTextFieldState<T>(initialChips: List<T> = emptyList()) {
    var chips: List<T>   // observable, read-only from outside

    fun addChip(chip: T)
    fun removeChip(chip: T)
    fun removeChipAt(index: Int)
    fun clearChips()
}
```

## Creating state

### In a composable

```kotlin
// Empty
val state = rememberChipTextFieldState<String>()

// With initial chips
val state = rememberChipTextFieldState(
    initialChips = listOf("Kotlin", "Compose")
)
```

### Outside a composable

```kotlin
val state = ChipTextFieldState(initialChips = listOf("Tag1", "Tag2"))
```

## Methods

| Method | Description |
|--------|-------------|
| `addChip(chip)` | Appends a chip to the list. |
| `removeChip(chip)` | Removes the first occurrence by equality. |
| `removeChipAt(index)` | Removes the chip at the given index. |
| `clearChips()` | Removes all chips. |

## Observing chips

`chips` is backed by `mutableStateOf` — reading it in a composable triggers recomposition when the list changes:

```kotlin
val state = rememberChipTextFieldState<String>()

// Reactively displays chip count
Text("${state.chips.size} chips")

// Iterate
state.chips.forEach { chip ->
    println(chip)
}
```

## Programmatic control

```kotlin
// Add chips from external data
LaunchedEffect(contacts) {
    contacts.forEach { state.addChip(it) }
}

// Clear on button press
Button(onClick = { state.clearChips() }) {
    Text("Clear all")
}
```
