package io.github.aldefy.chiptextfield.sample

import androidx.compose.runtime.Composable

@Composable
actual fun PlatformBackHandler(enabled: Boolean, onBack: () -> Unit) {
    // No system back on desktop — toolbar back button handles navigation
}
