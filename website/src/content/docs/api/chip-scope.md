---
title: ChipScope
description: Scope for chip slot content
---

## Definition

```kotlin
@Immutable
class ChipScope<T>(
    val chip: T,
    val enabled: Boolean,
    val colors: ChipTextFieldColors,
    val onRemove: () -> Unit,
)
```

`ChipScope` is the receiver for `chipLeadingIcon` and `chipTrailingIcon` lambdas. It provides access to the chip data and state for conditional rendering.

## Properties

| Property | Type | Description |
|----------|------|-------------|
| `chip` | `T` | The chip data object. Use to read custom fields (e.g., `chip.status`, `chip.color`). |
| `enabled` | `Boolean` | Whether the chip is in an editable state. |
| `colors` | `ChipTextFieldColors` | The current color theme. Use `colors.chipTextColor` for consistent styling. |
| `onRemove` | `() -> Unit` | Call this to remove the chip. Essential for custom trailing close buttons. |

## Usage in chipLeadingIcon

```kotlin
chipLeadingIcon = {
    // `this` is ChipScope<StatusItem>
    Canvas(modifier = Modifier.size(10.dp)) {
        drawCircle(color = chip.status.color)
    }
}
```

## Usage in chipTrailingIcon

```kotlin
chipTrailingIcon = {
    Icon(
        imageVector = Icons.Default.Close,
        contentDescription = "Remove ${chip.label}",
        modifier = Modifier
            .size(16.dp)
            .clickable(onClick = onRemove), // removes the chip
        tint = colors.chipTextColor,
    )
}
```

:::note
When `chipTrailingIcon` is `null`, the default close button (a Canvas-drawn X) is used automatically. Set `chipTrailingIcon` only when you want a custom close icon or additional trailing content.
:::
