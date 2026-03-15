---
title: Installation
description: Add ChipTextField to your project
---

## Gradle (Kotlin DSL)

```kotlin
dependencies {
    implementation("io.github.aldefy:chip-textfield:1.0.0-alpha01")
}
```

## Version Catalog

```toml
# gradle/libs.versions.toml
[versions]
chip-textfield = "1.0.0-alpha01"

[libraries]
chip-textfield = { group = "io.github.aldefy", name = "chip-textfield", version.ref = "chip-textfield" }
```

Then in your `build.gradle.kts`:

```kotlin
dependencies {
    implementation(libs.chip.textfield)
}
```

## Platform artifacts

The library automatically resolves the correct artifact for your platform:

| Platform | Artifact |
|----------|----------|
| Android | `chip-textfield-android` (AAR) |
| JVM / Desktop | `chip-textfield-jvm` (JAR) |
| iOS arm64 | `chip-textfield-iosarm64` (klib) |
| iOS simulator | `chip-textfield-iossimulatorarm64` (klib) |
| iOS x64 | `chip-textfield-iosx64` (klib) |
| Web (Wasm) | `chip-textfield-wasm-js` (klib) |

## Requirements

- Kotlin 2.1.20+
- Compose Multiplatform 1.8.0+
- Android: minSdk 23, compileSdk 35
