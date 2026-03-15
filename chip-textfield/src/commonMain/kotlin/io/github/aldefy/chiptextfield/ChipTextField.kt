package io.github.aldefy.chiptextfield

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ExperimentalLayoutApi
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Shape
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.runtime.key as composeKey
import androidx.compose.ui.input.key.Key
import androidx.compose.ui.input.key.KeyEventType
import androidx.compose.ui.input.key.key
import androidx.compose.ui.input.key.onPreviewKeyEvent
import androidx.compose.ui.input.key.type
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

/**
 * A text field that converts typed text into chips.
 *
 * Built entirely on Compose Foundation — no Material dependency.
 * Works with Material 2, Material 3, or custom design systems.
 *
 * Uses the slot pattern for chip customization:
 * - [chipContent]: Full control over chip rendering (overrides all other chip slots)
 * - [chipLeadingIcon]: Icon/avatar before the label (uses [ChipScope] receiver)
 * - [chipTrailingIcon]: Custom trailing icon (uses [ChipScope] receiver, defaults to close button)
 *
 * @param T the type of data each chip represents.
 * @param state the [ChipTextFieldState] managing the chip list.
 * @param onCreateChip called to create a chip from text input. Return null to reject.
 * @param modifier modifier for the outer container.
 * @param chipContent full custom composable for rendering each chip. When set, [chipLeadingIcon] and [chipTrailingIcon] are ignored.
 * @param chipLabel maps a chip to its display label. Defaults to [toString].
 * @param chipLeadingIcon slot for a leading icon/avatar inside the default chip. Receives [ChipScope].
 * @param chipTrailingIcon slot for a trailing icon inside the default chip. Defaults to close button. Receives [ChipScope].
 * @param onChipRemoved optional callback when a chip is removed.
 * @param onQueryChanged called when the text input changes, useful for filtering suggestions.
 * @param suggestionContent composable rendered below the field to show suggestions.
 * @param placeholder composable shown when there are no chips and no text.
 * @param leadingIcon optional leading icon inside the field (before all chips).
 * @param enabled whether the field is editable.
 * @param readOnly whether chips can be added/removed.
 * @param delimiters characters that trigger chip creation.
 * @param maxChips maximum number of chips allowed.
 * @param colors color configuration.
 * @param shape shape of the outer container.
 */
@OptIn(ExperimentalLayoutApi::class)
@Composable
fun <T> ChipTextField(
    state: ChipTextFieldState<T>,
    onCreateChip: (String) -> T?,
    modifier: Modifier = Modifier,
    chipContent: (@Composable (T, onRemove: () -> Unit) -> Unit)? = null,
    chipLabel: (T) -> String = { it.toString() },
    chipLeadingIcon: (@Composable ChipScope<T>.() -> Unit)? = null,
    chipTrailingIcon: (@Composable ChipScope<T>.() -> Unit)? = null,
    onChipRemoved: ((T) -> Unit)? = null,
    onQueryChanged: ((String) -> Unit)? = null,
    suggestionContent: @Composable ((query: String, onSuggestionSelected: (T) -> Unit) -> Unit)? = null,
    placeholder: @Composable (() -> Unit)? = null,
    leadingIcon: @Composable (() -> Unit)? = null,
    enabled: Boolean = true,
    readOnly: Boolean = false,
    delimiters: Set<Char> = ChipTextFieldDefaults.delimiters,
    maxChips: Int = Int.MAX_VALUE,
    colors: ChipTextFieldColors = ChipTextFieldDefaults.colors(),
    shape: Shape = ChipTextFieldDefaults.shape,
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var textFieldValue by remember { mutableStateOf(TextFieldValue("")) }
    val isEditable = enabled && !readOnly
    val canAddChips = isEditable && state.chips.size < maxChips

    // Remembered text style to avoid allocation on recomposition
    val inputTextStyle = remember(colors.textColor) {
        TextStyle(color = colors.textColor, fontSize = 16.sp)
    }

    fun addChipAndClear(chip: T) {
        if (state.chips.size >= maxChips) return
        state.addChip(chip)
        textFieldValue = TextFieldValue("")
        onQueryChanged?.invoke("")
    }

    fun commitText(text: String) {
        if (!isEditable || state.chips.size >= maxChips) return
        val trimmed = text.trim()
        if (trimmed.isEmpty()) return
        val chip = onCreateChip(trimmed)
        if (chip != null) {
            addChipAndClear(chip)
        }
    }

    fun processInput(newValue: TextFieldValue) {
        val newText = newValue.text
        val hasDelimiter = newText.any { it in delimiters }
        if (hasDelimiter) {
            val parts = newText.split(*delimiters.toCharArray())
            parts.dropLast(1).forEach { part -> commitText(part) }
            val remaining = parts.last()
            textFieldValue = TextFieldValue(remaining)
            onQueryChanged?.invoke(remaining)
        } else {
            textFieldValue = newValue
            onQueryChanged?.invoke(newText)
        }
    }

    fun removeLastChip() {
        if (!isEditable || state.chips.isEmpty()) return
        val last = state.chips.last()
        state.removeChipAt(state.chips.lastIndex)
        onChipRemoved?.invoke(last)
    }

    Column(modifier = modifier) {
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .defaultMinSize(minHeight = 48.dp)
                .clip(shape)
                .background(colors.containerColor)
                .padding(horizontal = 12.dp, vertical = 8.dp),
            horizontalArrangement = Arrangement.spacedBy(6.dp),
            verticalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            if (leadingIcon != null) {
                Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                    leadingIcon()
                }
            }

            val chips = state.chips
            chips.forEachIndexed { index, chip ->
                // Use key for stable identity — avoids recomposing unchanged chips
                composeKey(chip, index) {
                    Box(modifier = Modifier.align(Alignment.CenterVertically)) {
                        if (chipContent != null) {
                            chipContent(
                                chip,
                                remember(index, chip) {
                                    {
                                        if (!readOnly) {
                                            state.removeChipAt(index)
                                            onChipRemoved?.invoke(chip)
                                        }
                                    }
                                },
                            )
                        } else {
                            DefaultChipContent(
                                chip = chip,
                                label = chipLabel(chip),
                                enabled = isEditable,
                                colors = colors,
                                leadingIcon = chipLeadingIcon,
                                trailingIcon = chipTrailingIcon,
                                onRemove = remember(index, chip) {
                                    {
                                        if (!readOnly) {
                                            state.removeChipAt(index)
                                            onChipRemoved?.invoke(chip)
                                        }
                                    }
                                },
                            )
                        }
                    }
                }
            }

            if (canAddChips) {
                BasicTextField(
                    value = textFieldValue,
                    onValueChange = ::processInput,
                    modifier = Modifier
                        .weight(1f, fill = false)
                        .defaultMinSize(minWidth = 80.dp)
                        .heightIn(min = 32.dp)
                        .align(Alignment.CenterVertically)
                        .onPreviewKeyEvent { event ->
                            if (event.type == KeyEventType.KeyDown &&
                                event.key == Key.Backspace &&
                                textFieldValue.text.isEmpty()
                            ) {
                                removeLastChip()
                                true
                            } else {
                                false
                            }
                        },
                    textStyle = inputTextStyle,
                    cursorBrush = SolidColor(colors.cursorColor),
                    singleLine = true,
                    keyboardOptions = KeyboardOptions(imeAction = ImeAction.Done),
                    keyboardActions = KeyboardActions(
                        onDone = {
                            commitText(textFieldValue.text)
                            textFieldValue = TextFieldValue("")
                            onQueryChanged?.invoke("")
                            keyboardController?.hide()
                        },
                    ),
                    decorationBox = { innerTextField ->
                        Box(contentAlignment = Alignment.CenterStart) {
                            if (textFieldValue.text.isEmpty() && chips.isEmpty() && placeholder != null) {
                                placeholder()
                            }
                            innerTextField()
                        }
                    },
                )
            }
        }

        // Suggestion dropdown area
        if (suggestionContent != null && textFieldValue.text.isNotEmpty()) {
            suggestionContent(textFieldValue.text) { chip ->
                addChipAndClear(chip)
            }
        }
    }
}
