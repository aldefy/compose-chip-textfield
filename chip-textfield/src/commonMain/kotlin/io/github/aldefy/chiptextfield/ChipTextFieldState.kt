package io.github.aldefy.chiptextfield

import androidx.compose.runtime.Composable
import androidx.compose.runtime.Stable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue

/**
 * State holder for [ChipTextField].
 *
 * @param T the type of data each chip represents.
 * @param initialChips optional list of chips to start with.
 */
@Stable
class ChipTextFieldState<T>(initialChips: List<T> = emptyList()) {

    /**
     * The current list of chips. Observable via Compose snapshot state.
     */
    var chips: List<T> by mutableStateOf(initialChips)
        internal set

    /**
     * Adds a chip to the end of the list.
     */
    fun addChip(chip: T) {
        chips = chips + chip
    }

    /**
     * Removes the first occurrence of [chip].
     */
    fun removeChip(chip: T) {
        chips = chips - chip
    }

    /**
     * Removes the chip at [index].
     * @throws IndexOutOfBoundsException if [index] is out of range.
     */
    fun removeChipAt(index: Int) {
        chips = chips.toMutableList().apply { removeAt(index) }
    }

    /**
     * Clears all chips.
     */
    fun clearChips() {
        chips = emptyList()
    }
}

/**
 * Creates and remembers a [ChipTextFieldState].
 */
@Composable
fun <T> rememberChipTextFieldState(
    initialChips: List<T> = emptyList(),
): ChipTextFieldState<T> = remember { ChipTextFieldState(initialChips) }
