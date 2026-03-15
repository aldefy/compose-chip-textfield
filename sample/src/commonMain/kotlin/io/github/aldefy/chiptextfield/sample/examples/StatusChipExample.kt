package io.github.aldefy.chiptextfield.sample.examples

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.graphics.drawscope.DrawScope
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.aldefy.chiptextfield.ChipTextField
import io.github.aldefy.chiptextfield.rememberChipTextFieldState

enum class Status(val color: Color, val label: String) {
    Active(Color(0xFF4CAF50), "Active"),
    Pending(Color(0xFFFFC107), "Pending"),
    Inactive(Color(0xFFF44336), "Inactive"),
}

private val iconOptions = listOf("⭐", "🔥", "📌", "🚀", "💡", "🎯")

data class StatusItem(val label: String, val status: Status, val icon: String)

/** Canvas checkmark */
private fun DrawScope.drawCheck(color: Color) {
    val s = size.width
    val stroke = 2.dp.toPx()
    drawLine(color, Offset(s * 0.2f, s * 0.5f), Offset(s * 0.42f, s * 0.72f), stroke, StrokeCap.Round)
    drawLine(color, Offset(s * 0.42f, s * 0.72f), Offset(s * 0.8f, s * 0.28f), stroke, StrokeCap.Round)
}

/** Canvas hourglass */
private fun DrawScope.drawHourglass(color: Color) {
    val s = size.width
    val stroke = 1.8f.dp.toPx()
    drawLine(color, Offset(s * 0.2f, s * 0.18f), Offset(s * 0.8f, s * 0.18f), stroke, StrokeCap.Round)
    drawLine(color, Offset(s * 0.2f, s * 0.18f), Offset(s * 0.5f, s * 0.5f), stroke, StrokeCap.Round)
    drawLine(color, Offset(s * 0.8f, s * 0.18f), Offset(s * 0.5f, s * 0.5f), stroke, StrokeCap.Round)
    drawLine(color, Offset(s * 0.2f, s * 0.82f), Offset(s * 0.8f, s * 0.82f), stroke, StrokeCap.Round)
    drawLine(color, Offset(s * 0.2f, s * 0.82f), Offset(s * 0.5f, s * 0.5f), stroke, StrokeCap.Round)
    drawLine(color, Offset(s * 0.8f, s * 0.82f), Offset(s * 0.5f, s * 0.5f), stroke, StrokeCap.Round)
}

/** Canvas X */
private fun DrawScope.drawCross(color: Color) {
    val s = size.width
    val pad = s * 0.22f
    val stroke = 2.dp.toPx()
    drawLine(color, Offset(pad, pad), Offset(s - pad, s - pad), stroke, StrokeCap.Round)
    drawLine(color, Offset(s - pad, pad), Offset(pad, s - pad), stroke, StrokeCap.Round)
}

@Composable
private fun StatusDot(status: Status, modifier: Modifier = Modifier) {
    Canvas(modifier = modifier.size(14.dp)) {
        drawCircle(color = status.color, style = Stroke(width = 1.5.dp.toPx()))
        when (status) {
            Status.Active -> drawCheck(status.color)
            Status.Pending -> drawHourglass(status.color)
            Status.Inactive -> drawCross(status.color)
        }
    }
}

@Composable
fun StatusChipExample() {
    val state = rememberChipTextFieldState(
        initialChips = listOf(
            StatusItem("Server A", Status.Active, "🚀"),
            StatusItem("Deploy", Status.Pending, "🔥"),
        ),
    )
    var selectedStatus by remember { mutableStateOf(Status.Active) }
    var selectedIcon by remember { mutableStateOf(iconOptions[0]) }

    Column {
        // Status selector
        BasicText(
            text = "Status",
            style = TextStyle(fontSize = 12.sp, color = Color.Gray),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.spacedBy(8.dp),
        ) {
            Status.entries.forEach { status ->
                val isSelected = status == selectedStatus
                val shape = RoundedCornerShape(8.dp)
                Row(
                    modifier = Modifier
                        .clip(shape)
                        .then(
                            if (isSelected) {
                                Modifier
                                    .background(status.color.copy(alpha = 0.12f))
                                    .border(1.5.dp, status.color, shape)
                            } else {
                                Modifier.border(1.dp, Color.Gray.copy(alpha = 0.4f), shape)
                            },
                        )
                        .clickable { selectedStatus = status }
                        .padding(horizontal = 12.dp, vertical = 8.dp),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(6.dp),
                ) {
                    StatusDot(status)
                    BasicText(
                        text = status.label,
                        style = TextStyle(
                            fontSize = 13.sp,
                            color = if (isSelected) status.color else Color.Gray,
                        ),
                    )
                }
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        // Icon selector
        BasicText(
            text = "Icon",
            style = TextStyle(fontSize = 12.sp, color = Color.Gray),
        )
        Spacer(modifier = Modifier.height(4.dp))
        Row(
            horizontalArrangement = Arrangement.spacedBy(6.dp),
        ) {
            iconOptions.forEach { icon ->
                val isSelected = icon == selectedIcon
                BasicText(
                    text = icon,
                    style = TextStyle(fontSize = 20.sp, textAlign = TextAlign.Center),
                    modifier = Modifier
                        .size(40.dp)
                        .clip(CircleShape)
                        .then(
                            if (isSelected) {
                                Modifier
                                    .background(
                                        selectedStatus.color.copy(alpha = 0.12f),
                                        CircleShape,
                                    )
                                    .border(1.5.dp, selectedStatus.color, CircleShape)
                            } else {
                                Modifier.border(1.dp, Color.Gray.copy(alpha = 0.3f), CircleShape)
                            },
                        )
                        .clickable { selectedIcon = icon }
                        .padding(6.dp),
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        ChipTextField(
            state = state,
            onCreateChip = { text ->
                val trimmed = text.trim()
                if (trimmed.isBlank()) null else StatusItem(trimmed, selectedStatus, selectedIcon)
            },
            chipLabel = { it.label },
            chipLeadingIcon = {
                // emoji icon + status dot — composable lambda drawn before the label text
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(4.dp),
                ) {
                    BasicText(
                        text = chip.icon,
                        style = TextStyle(fontSize = 14.sp),
                    )
                    StatusDot(chip.status, Modifier.size(10.dp))
                }
            },
            chipTrailingIcon = {
                val color = colors.chipTextColor
                Canvas(
                    modifier = Modifier
                        .padding(start = 4.dp)
                        .size(16.dp)
                        .clickable(onClick = onRemove),
                ) {
                    drawCross(color)
                }
            },
            placeholder = {
                Text(
                    text = "Pick status & icon, then type...",
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                )
            },
        )
    }
}
