---
title: Chip Customization
description: Three levels of chip appearance control
---

ChipTextField provides three levels of customization, from simple color tweaks to full rendering control.

## Level 1: Colors and Shape

Customize the default chip appearance without changing its structure:

```kotlin
ChipTextField(
    state = state,
    onCreateChip = { it },
    colors = ChipTextFieldDefaults.colors(
        chipBackgroundColor = Color(0xFFE3F2FD),
        chipTextColor = Color(0xFF1565C0),
        chipBorderColor = Color(0xFF90CAF9),
    ),
    shape = RoundedCornerShape(12.dp),
)
```

### Available color tokens

| Token | Default | Description |
|-------|---------|-------------|
| `chipBackgroundColor` | Surface variant | Chip fill |
| `chipTextColor` | On surface | Chip label |
| `chipBorderColor` | Outline | Chip border |
| `cursorColor` | Primary | Text cursor |
| `textColor` | On surface | Input text |
| `placeholderColor` | On surface variant | Placeholder |
| `containerColor` | Surface | Outer container |
| `disabledChipBackgroundColor` | Surface dim | Disabled chip fill |
| `disabledChipTextColor` | On surface (38% alpha) | Disabled chip label |

## Level 2: Slot Icons

Add leading and/or trailing content to the default chip using `ChipScope`:

```kotlin
ChipTextField(
    state = state,
    onCreateChip = { StatusItem(it, Status.Active) },
    chipLabel = { it.label },
    chipLeadingIcon = {
        // `this` is ChipScope<T>
        // Access: chip, enabled, colors, onRemove
        Canvas(modifier = Modifier.size(10.dp)) {
            drawCircle(color = chip.status.color)
        }
    },
    chipTrailingIcon = {
        Canvas(
            modifier = Modifier
                .size(16.dp)
                .clickable(onClick = onRemove), // from ChipScope
        ) {
            // Draw custom X icon
        }
    },
)
```

### ChipScope properties

The `chipLeadingIcon` and `chipTrailingIcon` lambdas receive `ChipScope<T>`:

| Property | Type | Description |
|----------|------|-------------|
| `chip` | `T` | The chip data object |
| `enabled` | `Boolean` | Whether the chip is editable |
| `colors` | `ChipTextFieldColors` | Current color configuration |
| `onRemove` | `() -> Unit` | Call to remove this chip |

:::tip
Use `onRemove` in `chipTrailingIcon` to create custom close buttons. Without it, the default X button is used.
:::

## Level 3: Full Custom Content

Replace the entire chip rendering with `chipContent`:

```kotlin
ChipTextField(
    state = state,
    onCreateChip = { Priority(it, level = 1) },
    chipContent = { chip, onRemove ->
        // Full control — render anything
        Row(
            modifier = Modifier
                .clip(RoundedCornerShape(16.dp))
                .background(colorForLevel(chip.level))
                .padding(horizontal = 12.dp, vertical = 6.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Text(chip.label, color = textColorForLevel(chip.level))
            Icon(
                Icons.Default.Close,
                contentDescription = "Remove",
                modifier = Modifier
                    .padding(start = 4.dp)
                    .size(14.dp)
                    .clickable(onClick = onRemove),
            )
        }
    },
)
```

:::caution
When `chipContent` is set, `chipLeadingIcon` and `chipTrailingIcon` are ignored.
:::
