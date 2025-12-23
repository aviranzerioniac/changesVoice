package voice.core.ui.components

import androidx.compose.foundation.layout.RowScope
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector

data class VoiceBottomNavItem(
  val label: String,
  val icon: ImageVector,
  val selected: Boolean,
  val onClick: () -> Unit,
)

@Composable
fun VoiceBottomNavBar(
  items: List<VoiceBottomNavItem>,
  modifier: Modifier = Modifier,
) {
  NavigationBar(
    modifier = modifier,
    containerColor = MaterialTheme.colorScheme.surface,
    contentColor = MaterialTheme.colorScheme.onSurface,
  ) {
    items.forEach { item ->
      VoiceNavigationBarItem(
        label = item.label,
        icon = item.icon,
        selected = item.selected,
        onClick = item.onClick,
      )
    }
  }
}

@Composable
private fun RowScope.VoiceNavigationBarItem(
  label: String,
  icon: ImageVector,
  selected: Boolean,
  onClick: () -> Unit,
) {
  NavigationBarItem(
    selected = selected,
    onClick = onClick,
    icon = {
      Icon(
        imageVector = icon,
        contentDescription = label,
      )
    },
    label = {
      Text(
        text = label,
        style = MaterialTheme.typography.labelSmall,
      )
    },
    colors = NavigationBarItemDefaults.colors(
      selectedIconColor = MaterialTheme.colorScheme.primary,
      selectedTextColor = MaterialTheme.colorScheme.primary,
      unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
      unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
      indicatorColor = MaterialTheme.colorScheme.primaryContainer.copy(alpha = 0.3f),
    ),
  )
}

val DefaultBottomNavItems = listOf(
  VoiceBottomNavItem(
    label = "Home",
    icon = Icons.Default.Home,
    selected = true,
    onClick = {},
  ),
  VoiceBottomNavItem(
    label = "Search",
    icon = Icons.Default.Search,
    selected = false,
    onClick = {},
  ),
  VoiceBottomNavItem(
    label = "Library",
    icon = Icons.Outlined.Description,
    selected = false,
    onClick = {},
  ),
)
