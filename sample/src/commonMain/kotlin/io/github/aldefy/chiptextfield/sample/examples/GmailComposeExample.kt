package io.github.aldefy.chiptextfield.sample.examples

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import io.github.aldefy.chiptextfield.ChipTextField
import io.github.aldefy.chiptextfield.ChipTextFieldDefaults
import io.github.aldefy.chiptextfield.rememberChipTextFieldState

data class Contact(
    val name: String,
    val email: String,
) {
    val initials: String
        get() = name.split(" ")
            .filter { it.isNotBlank() }
            .take(2)
            .map { it.first().uppercaseChar() }
            .joinToString("")

    val avatarColor: Color
        get() {
            val colors = listOf(
                Color(0xFF1A73E8),
                Color(0xFF34A853),
                Color(0xFFEA4335),
                Color(0xFFFBBC04),
                Color(0xFF9334E6),
                Color(0xFFE8710A),
                Color(0xFF00ACC1),
            )
            val idx = (name.hashCode() and Int.MAX_VALUE) % colors.size
            return colors[idx]
        }
}

private val allContacts = listOf(
    Contact("Raina Shah", "raina@travv.world"),
    Contact("Adit Lal", "adit@travv.world"),
    Contact("John Doe", "john@gmail.com"),
    Contact("Sarah Connor", "sarah@gmail.com"),
    Contact("Alex Kim", "alex@company.com"),
    Contact("Priya Patel", "priya@outlook.com"),
    Contact("Mike Chen", "mike@startup.io"),
    Contact("Lisa Wang", "lisa@design.co"),
)

private val emailRegex = Regex("^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$")

private fun createContact(text: String): Contact? {
    val trimmed = text.trim()
    // Check known contacts by name or email
    val byName = allContacts.find { it.name.equals(trimmed, ignoreCase = true) }
    if (byName != null) return byName
    val byEmail = allContacts.find { it.email.equals(trimmed, ignoreCase = true) }
    if (byEmail != null) return byEmail
    // Validate as email
    if (!emailRegex.matches(trimmed)) return null
    val name = trimmed.substringBefore("@")
        .replace(".", " ")
        .split(" ")
        .joinToString(" ") { it.replaceFirstChar { c -> c.uppercaseChar() } }
    return Contact(name = name, email = trimmed.lowercase())
}

private fun filterSuggestions(query: String, existing: List<Contact>): List<Contact> {
    if (query.isBlank()) return emptyList()
    val q = query.lowercase()
    val existingEmails = existing.map { it.email }.toSet()
    return allContacts.filter { contact ->
        contact.email !in existingEmails &&
            (contact.name.lowercase().contains(q) || contact.email.lowercase().contains(q))
    }
}

@Composable
fun GmailComposeExample() {
    val toState = rememberChipTextFieldState(
        initialChips = listOf(
            Contact("Raina Shah", "raina@travv.world"),
        ),
    )
    val ccState = rememberChipTextFieldState<Contact>()

    Surface(
        shape = RoundedCornerShape(12.dp),
        tonalElevation = 1.dp,
        modifier = Modifier.fillMaxWidth(),
    ) {
        Column(modifier = Modifier.padding(vertical = 8.dp)) {
            GmailField(label = "To", state = toState)

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
            )

            GmailField(label = "Cc", state = ccState)

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
            )

            BasicText(
                text = "Trip planning for Southeast Asia \uD83C\uDF0F",
                style = TextStyle(
                    fontSize = 16.sp,
                    color = MaterialTheme.colorScheme.onSurface,
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            )

            HorizontalDivider(
                modifier = Modifier.padding(horizontal = 16.dp),
                color = MaterialTheme.colorScheme.outlineVariant.copy(alpha = 0.5f),
            )

            BasicText(
                text = "Hey! Let's finalize the itinerary for...",
                style = TextStyle(
                    fontSize = 14.sp,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                ),
                modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            )
        }
    }
}

@Composable
private fun GmailField(
    label: String,
    state: io.github.aldefy.chiptextfield.ChipTextFieldState<Contact>,
) {
    var query by remember { mutableStateOf("") }

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(start = 16.dp, end = 8.dp),
        verticalAlignment = Alignment.Top,
    ) {
        Text(
            text = label,
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            modifier = Modifier.padding(top = 14.dp).width(28.dp),
        )

        ChipTextField(
            state = state,
            onCreateChip = ::createContact,
            modifier = Modifier.weight(1f),
            delimiters = setOf(',', '\n'),
            onQueryChanged = { query = it },
            colors = ChipTextFieldDefaults.colors(
                containerColor = Color.Transparent,
                chipBackgroundColor = MaterialTheme.colorScheme.secondaryContainer,
                chipTextColor = MaterialTheme.colorScheme.onSecondaryContainer,
                chipBorderColor = Color.Transparent,
                textColor = MaterialTheme.colorScheme.onSurface,
                placeholderColor = MaterialTheme.colorScheme.onSurfaceVariant,
                cursorColor = MaterialTheme.colorScheme.primary,
            ),
            chipContent = { contact, onRemove ->
                GmailChip(contact = contact, onRemove = onRemove)
            },
            suggestionContent = { currentQuery, onSelect ->
                val suggestions = filterSuggestions(currentQuery, state.chips)
                if (suggestions.isNotEmpty()) {
                    Surface(
                        shape = RoundedCornerShape(8.dp),
                        tonalElevation = 4.dp,
                        shadowElevation = 4.dp,
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(top = 4.dp),
                    ) {
                        Column {
                            suggestions.forEach { contact ->
                                SuggestionItem(
                                    contact = contact,
                                    onClick = { onSelect(contact) },
                                )
                            }
                        }
                    }
                }
            },
            placeholder = {
                Text(
                    text = "Add recipients",
                    style = MaterialTheme.typography.bodyMedium,
                    color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
                )
            },
        )
    }
}

@Composable
private fun SuggestionItem(
    contact: Contact,
    onClick: () -> Unit,
) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick)
            .padding(horizontal = 12.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Box(
            modifier = Modifier
                .size(32.dp)
                .clip(CircleShape)
                .background(contact.avatarColor),
            contentAlignment = Alignment.Center,
        ) {
            BasicText(
                text = contact.initials,
                style = TextStyle(
                    color = Color.White,
                    fontSize = 12.sp,
                    fontWeight = FontWeight.Medium,
                ),
            )
        }

        Spacer(modifier = Modifier.width(12.dp))

        Column {
            BasicText(
                text = contact.name,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurface,
                    fontSize = 14.sp,
                    fontWeight = FontWeight.Medium,
                ),
            )
            BasicText(
                text = contact.email,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontSize = 12.sp,
                ),
            )
        }
    }
}

@Composable
private fun GmailChip(
    contact: Contact,
    onRemove: () -> Unit,
) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = MaterialTheme.colorScheme.secondaryContainer,
        modifier = Modifier.clickable(onClick = onRemove),
    ) {
        Row(
            modifier = Modifier.padding(start = 2.dp, end = 10.dp, top = 2.dp, bottom = 2.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Box(
                modifier = Modifier
                    .size(28.dp)
                    .clip(CircleShape)
                    .background(contact.avatarColor),
                contentAlignment = Alignment.Center,
            ) {
                BasicText(
                    text = contact.initials,
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 11.sp,
                        fontWeight = FontWeight.Medium,
                    ),
                )
            }

            Spacer(modifier = Modifier.width(6.dp))

            BasicText(
                text = contact.name,
                style = TextStyle(
                    color = MaterialTheme.colorScheme.onSecondaryContainer,
                    fontSize = 14.sp,
                ),
            )
        }
    }
}
