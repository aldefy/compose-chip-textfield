package io.github.aldefy.chiptextfield.sample

import androidx.compose.ui.window.Window
import androidx.compose.ui.window.application

fun main() = application {
    Window(
        onCloseRequest = ::exitApplication,
        title = "ChipTextField Sample",
    ) {
        SampleApp()
    }
}
