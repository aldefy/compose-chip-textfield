---
title: Examples
description: Sample app with 6 interactive demos
---

The sample app includes a catalog of 6 examples showcasing different ChipTextField capabilities.

## Running the sample

```bash
# Android
./gradlew :sample:installDebug

# Desktop (JVM)
./gradlew :sample:run
```

## Catalog

| Example | What it demonstrates |
|---------|---------------------|
| **Basic Input** | Minimal chip field — type and press Enter |
| **Email Input** | Email validation + leading icon + space delimiter |
| **Gmail Compose** | Generic `Contact` type + suggestion dropdown + avatar chips |
| **Tags (max 5)** | Auto `#` prefix + `maxChips = 5` |
| **Priority Chips** | Full `chipContent` slot with color-coded priority levels |
| **Status Chips** | `chipLeadingIcon` (emoji + status dot) + `chipTrailingIcon` (custom X) |

## Screenshots

![Catalog](https://raw.githubusercontent.com/aldefy/compose-chip-textfield/main/screenshots/catalog.png)

![Status Chips](https://raw.githubusercontent.com/aldefy/compose-chip-textfield/main/screenshots/status_chips.png)

## Source code

All examples are in [`sample/src/commonMain/.../examples/`](https://github.com/aldefy/compose-chip-textfield/tree/main/sample/src/commonMain/kotlin/io/github/aldefy/chiptextfield/sample/examples):

- [`BasicExample.kt`](https://github.com/aldefy/compose-chip-textfield/blob/main/sample/src/commonMain/kotlin/io/github/aldefy/chiptextfield/sample/examples/BasicExample.kt)
- [`EmailInputExample.kt`](https://github.com/aldefy/compose-chip-textfield/blob/main/sample/src/commonMain/kotlin/io/github/aldefy/chiptextfield/sample/examples/EmailInputExample.kt)
- [`GmailComposeExample.kt`](https://github.com/aldefy/compose-chip-textfield/blob/main/sample/src/commonMain/kotlin/io/github/aldefy/chiptextfield/sample/examples/GmailComposeExample.kt)
- [`TagsExample.kt`](https://github.com/aldefy/compose-chip-textfield/blob/main/sample/src/commonMain/kotlin/io/github/aldefy/chiptextfield/sample/examples/TagsExample.kt)
- [`CustomChipExample.kt`](https://github.com/aldefy/compose-chip-textfield/blob/main/sample/src/commonMain/kotlin/io/github/aldefy/chiptextfield/sample/examples/CustomChipExample.kt)
- [`StatusChipExample.kt`](https://github.com/aldefy/compose-chip-textfield/blob/main/sample/src/commonMain/kotlin/io/github/aldefy/chiptextfield/sample/examples/StatusChipExample.kt)
