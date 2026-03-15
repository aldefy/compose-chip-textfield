---
title: Suggestions
description: Show a dropdown as users type
---

## Basic suggestions

Use `suggestionContent` to render a dropdown below the chip field. It receives the current query and a callback to select a suggestion:

```kotlin
val contacts = listOf(
    Contact("Alice", "alice@example.com"),
    Contact("Bob", "bob@example.com"),
    Contact("Carol", "carol@example.com"),
)
var filtered by remember { mutableStateOf(emptyList<Contact>()) }

ChipTextField(
    state = state,
    onCreateChip = { text ->
        contacts.find { it.email == text }
            ?: Contact(text, text)
    },
    chipLabel = { it.name },
    onQueryChanged = { query ->
        filtered = if (query.isBlank()) emptyList()
        else contacts.filter {
            it.name.contains(query, ignoreCase = true)
        }
    },
    suggestionContent = { query, onSuggestionSelected ->
        filtered.forEach { contact ->
            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .clickable { onSuggestionSelected(contact) }
                    .padding(12.dp),
            ) {
                Text(contact.name)
                Spacer(Modifier.width(8.dp))
                Text(contact.email, color = Color.Gray)
            }
        }
    },
)
```

## How it works

1. User types → `onQueryChanged` fires with the current text
2. You filter your data and store results in state
3. `suggestionContent` renders below the field (only when text is non-empty)
4. User taps a suggestion → `onSuggestionSelected` adds it as a chip and clears the input

:::tip
The suggestion dropdown is rendered inside the `ChipTextField`'s `Column`, directly below the `FlowRow`. Style it however you want — it's just a composable slot.
:::

## Gmail-style suggestions

The sample app includes a full Gmail Compose example with avatar initials and styled suggestion rows. See [`GmailComposeExample.kt`](https://github.com/aldefy/compose-chip-textfield/blob/main/sample/src/commonMain/kotlin/io/github/aldefy/chiptextfield/sample/examples/GmailComposeExample.kt).
