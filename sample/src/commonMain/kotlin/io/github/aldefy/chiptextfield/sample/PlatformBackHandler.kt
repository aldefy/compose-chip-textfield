package io.github.aldefy.chiptextfield.sample

import androidx.compose.runtime.Composable

@Composable
expect fun PlatformBackHandler(enabled: Boolean, onBack: () -> Unit)
