package io.github.aldefy.chiptextfield.sample

import androidx.compose.animation.AnimatedContent
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.animation.slideInHorizontally
import androidx.compose.animation.slideOutHorizontally
import androidx.compose.animation.togetherWith
import androidx.compose.foundation.clickable
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.automirrored.filled.Send
import androidx.compose.material.icons.filled.CheckCircle
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Code
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material.icons.filled.Email
import androidx.compose.material.icons.filled.Palette
import androidx.compose.material.icons.filled.Tag
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.material3.darkColorScheme
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import io.github.aldefy.chiptextfield.sample.examples.BasicExample
import io.github.aldefy.chiptextfield.sample.examples.CustomChipExample
import io.github.aldefy.chiptextfield.sample.examples.EmailInputExample
import io.github.aldefy.chiptextfield.sample.examples.GmailComposeExample
import io.github.aldefy.chiptextfield.sample.examples.StatusChipExample
import io.github.aldefy.chiptextfield.sample.examples.TagsExample

sealed interface Route
data object Home : Route
data class ExampleDetail(val example: Example) : Route

enum class Example(
    val title: String,
    val description: String,
    val icon: ImageVector,
) {
    Basic(
        title = "Basic Input",
        description = "Minimal chip text field — type and press Enter to create chips.",
        icon = Icons.Default.Edit,
    ),
    EmailInput(
        title = "Email Input",
        description = "Email-validated chips with leading icon and space delimiter.",
        icon = Icons.Default.Email,
    ),
    GmailCompose(
        title = "Gmail Compose",
        description = "Full To/Cc field with contact suggestions and avatar chips.",
        icon = Icons.AutoMirrored.Filled.Send,
    ),
    Tags(
        title = "Tags (max 5)",
        description = "Auto-prefixed #tags with a chip limit of 5.",
        icon = Icons.Default.Tag,
    ),
    CustomChip(
        title = "Priority Chips",
        description = "Custom chip rendering with priority-based colors via chipContent slot.",
        icon = Icons.Default.Palette,
    ),
    StatusChips(
        title = "Status Chips",
        description = "Colored status dots (chipLeadingIcon) and custom close (chipTrailingIcon).",
        icon = Icons.Default.CheckCircle,
    ),
}

@Composable
fun SampleApp() {
    MaterialTheme(
        colorScheme = if (isSystemInDarkTheme()) darkColorScheme() else lightColorScheme(),
    ) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background,
        ) {
            val backStack = remember { mutableStateListOf<Route>(Home) }
            val currentRoute = backStack.last()

            PlatformBackHandler(enabled = backStack.size > 1) {
                backStack.removeLastOrNull()
            }

            AnimatedContent(
                targetState = currentRoute,
                transitionSpec = {
                    if (targetState is ExampleDetail) {
                        (slideInHorizontally { it } + fadeIn()) togetherWith
                            (slideOutHorizontally { -it / 3 } + fadeOut())
                    } else {
                        (slideInHorizontally { -it } + fadeIn()) togetherWith
                            (slideOutHorizontally { it / 3 } + fadeOut())
                    }
                },
            ) { route ->
                when (route) {
                    is Home -> CatalogHome(
                        onExampleSelected = { backStack.add(ExampleDetail(it)) },
                    )
                    is ExampleDetail -> ExampleDetailScreen(
                        example = route.example,
                        onBack = { backStack.removeLastOrNull() },
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CatalogHome(onExampleSelected: (Example) -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = {
                    Column {
                        Text("ChipTextField")
                        Text(
                            text = "Material-free chip input for Compose Multiplatform",
                            style = MaterialTheme.typography.bodySmall,
                            color = MaterialTheme.colorScheme.onSurfaceVariant,
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
    ) { padding ->
        LazyVerticalGrid(
            columns = GridCells.Adaptive(280.dp),
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .padding(horizontal = 16.dp),
            verticalArrangement = Arrangement.spacedBy(12.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp),
        ) {
            items(Example.entries) { example ->
                ExampleCard(
                    example = example,
                    onClick = { onExampleSelected(example) },
                )
            }
            // Bottom spacer for last card visibility
            item { Spacer(modifier = Modifier.height(16.dp)) }
        }
    }
}

@Composable
private fun ExampleCard(example: Example, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable(onClick = onClick),
        colors = CardDefaults.cardColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f),
        ),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = example.icon,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.primary,
                modifier = Modifier.size(32.dp),
            )
            Spacer(modifier = Modifier.width(16.dp))
            Column(modifier = Modifier.weight(1f)) {
                Text(
                    text = example.title,
                    style = MaterialTheme.typography.titleMedium,
                )
                Text(
                    text = example.description,
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
            Icon(
                imageVector = Icons.Default.ChevronRight,
                contentDescription = null,
                tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ExampleDetailScreen(example: Example, onBack: () -> Unit) {
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text(example.title) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Back",
                        )
                    }
                },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.surface,
                ),
            )
        },
    ) { padding ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(padding)
                .imePadding()
                .verticalScroll(rememberScrollState())
                .padding(16.dp),
        ) {
            when (example) {
                Example.Basic -> BasicExample()
                Example.EmailInput -> EmailInputExample()
                Example.GmailCompose -> GmailComposeExample()
                Example.Tags -> TagsExample()
                Example.CustomChip -> CustomChipExample()
                Example.StatusChips -> StatusChipExample()
            }
        }
    }
}
