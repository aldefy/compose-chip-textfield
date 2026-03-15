---
title: ChipTextField
description: Main composable API reference
---

## Signature

```kotlin
@Composable
fun <T> ChipTextField(
    state: ChipTextFieldState<T>,
    onCreateChip: (String) -> T?,
    modifier: Modifier = Modifier,
    chipContent: (@Composable (T, onRemove: () -> Unit) -> Unit)? = null,
    chipLabel: (T) -> String = { it.toString() },
    chipLeadingIcon: (@Composable ChipScope<T>.() -> Unit)? = null,
    chipTrailingIcon: (@Composable ChipScope<T>.() -> Unit)? = null,
    onChipRemoved: ((T) -> Unit)? = null,
    onQueryChanged: ((String) -> Unit)? = null,
    suggestionContent: @Composable ((query: String, onSuggestionSelected: (T) -> Unit) -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    delimiters: Set<Char> = ChipTextFieldDefaults.delimiters,
    maxChips: Int = Int.MAX_VALUE,
    colors: ChipTextFieldColors = ChipTextFieldDefaults.colors(),
    shape: Shape = ChipTextFieldDefaults.shape,
)
```

## Parameters

| Parameter | Type | Default | Description |
|-----------|------|---------|-------------|
| `state` | `ChipTextFieldState<T>` | — | Manages the chip list. Create via `rememberChipTextFieldState()`. |
| `onCreateChip` | `(String) -> T?` | — | Factory function. Return `null` to reject input. |
| `modifier` | `Modifier` | `Modifier` | Modifier for the outer container. |
| `chipContent` | `@Composable (T, () -> Unit)?` | `null` | Full custom chip rendering. Overrides `chipLeadingIcon`/`chipTrailingIcon`. |
| `chipLabel` | `(T) -> String` | `toString()` | Extracts display label from chip data. |
| `chipLeadingIcon` | `@Composable ChipScope<T>.() -> Unit?` | `null` | Leading icon inside the default chip. |
| `chipTrailingIcon` | `@Composable ChipScope<T>.() -> Unit?` | `null` | Trailing icon. Defaults to close button when `null`. |
| `onChipRemoved` | `((T) -> Unit)?` | `null` | Callback when a chip is removed. |
| `onQueryChanged` | `((String) -> Unit)?` | `null` | Fires when input text changes. Use for suggestion filtering. |
| `suggestionContent` | `@Composable ((String, (T) -> Unit) -> Unit)?` | `null` | Suggestion dropdown rendered below the field. |
| `placeholder` | `@Composable (() -> Unit)?` | `null` | Shown when no chips and no text. |
| `leadingIcon` | `@Composable (() -> Unit)?` | `null` | Icon before all chips in the field. |
| `enabled` | `Boolean` | `true` | Whether the field is interactive. |
| `readOnly` | `Boolean` | `false` | Display chips without editing. |
| `delimiters` | `Set<Char>` | `{',', '\n'}` | Characters that trigger chip creation. |
| `maxChips` | `Int` | `Int.MAX_VALUE` | Maximum number of chips. Input hides at limit. |
| `colors` | `ChipTextFieldColors` | `ChipTextFieldDefaults.colors()` | Color configuration. |
| `shape` | `Shape` | `RoundedCornerShape(8.dp)` | Shape of the outer container. |

## Layout

ChipTextField uses `FlowRow` inside a `Column`:

```
Column
├── FlowRow
│   ├── [leadingIcon]
│   ├── Chip 1, Chip 2, ...
│   └── BasicTextField (input)
└── [suggestionContent] (when text is non-empty)
```

Chips wrap to the next line automatically. The input field takes remaining space with a minimum width of 80dp.
