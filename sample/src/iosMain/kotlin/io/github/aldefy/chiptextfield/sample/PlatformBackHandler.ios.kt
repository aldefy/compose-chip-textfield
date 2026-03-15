package io.github.aldefy.chiptextfield.sample

import androidx.compose.runtime.Composable

@Composable
actual fun PlatformBackHandler(enabled: Boolean, onBack: () -> Unit) {
    // iOS uses swipe-back gesture handled by the navigation controller
}
