package io.github.aldefy.chiptextfield

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertTrue

class ChipTextFieldStateTest {

    @Test
    fun initialStateIsEmpty() {
        val state = ChipTextFieldState<String>()
        assertTrue(state.chips.isEmpty())
    }

    @Test
    fun initialStateWithChips() {
        val state = ChipTextFieldState(listOf("a", "b", "c"))
        assertEquals(listOf("a", "b", "c"), state.chips)
    }

    @Test
    fun addChip() {
        val state = ChipTextFieldState<String>()
        state.addChip("hello")
        assertEquals(listOf("hello"), state.chips)
    }

    @Test
    fun addMultipleChips() {
        val state = ChipTextFieldState<String>()
        state.addChip("a")
        state.addChip("b")
        state.addChip("c")
        assertEquals(listOf("a", "b", "c"), state.chips)
    }

    @Test
    fun removeChip() {
        val state = ChipTextFieldState(listOf("a", "b", "c"))
        state.removeChip("b")
        assertEquals(listOf("a", "c"), state.chips)
    }

    @Test
    fun removeChipNotPresent() {
        val state = ChipTextFieldState(listOf("a", "b"))
        state.removeChip("z")
        assertEquals(listOf("a", "b"), state.chips)
    }

    @Test
    fun removeChipAt() {
        val state = ChipTextFieldState(listOf("a", "b", "c"))
        state.removeChipAt(1)
        assertEquals(listOf("a", "c"), state.chips)
    }

    @Test
    fun removeChipAtFirst() {
        val state = ChipTextFieldState(listOf("a", "b", "c"))
        state.removeChipAt(0)
        assertEquals(listOf("b", "c"), state.chips)
    }

    @Test
    fun removeChipAtLast() {
        val state = ChipTextFieldState(listOf("a", "b", "c"))
        state.removeChipAt(2)
        assertEquals(listOf("a", "b"), state.chips)
    }

    @Test
    fun removeChipAtInvalidIndex() {
        val state = ChipTextFieldState(listOf("a"))
        assertFailsWith<IndexOutOfBoundsException> {
            state.removeChipAt(5)
        }
    }

    @Test
    fun clearChips() {
        val state = ChipTextFieldState(listOf("a", "b", "c"))
        state.clearChips()
        assertTrue(state.chips.isEmpty())
    }

    @Test
    fun worksWithDataClasses() {
        data class Tag(val name: String, val color: String)
        val state = ChipTextFieldState<Tag>()
        state.addChip(Tag("kotlin", "purple"))
        state.addChip(Tag("compose", "green"))
        assertEquals(2, state.chips.size)
        state.removeChip(Tag("kotlin", "purple"))
        assertEquals(listOf(Tag("compose", "green")), state.chips)
    }

    @Test
    fun removeFirstDuplicateOnly() {
        val state = ChipTextFieldState(listOf("a", "b", "a", "c"))
        state.removeChip("a")
        assertEquals(listOf("b", "a", "c"), state.chips)
    }
}
