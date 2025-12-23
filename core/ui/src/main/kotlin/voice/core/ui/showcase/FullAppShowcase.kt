package voice.core.ui.showcase

import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Search
import androidx.compose.material.icons.outlined.Description
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import voice.core.ui.VoiceTheme
import voice.core.ui.components.VoiceBottomNavBar
import voice.core.ui.components.VoiceBottomNavItem
import voice.core.ui.screens.BookItem
import voice.core.ui.screens.VoiceLibraryScreen
import voice.core.ui.screens.VoiceSearchScreen
import voice.core.ui.screens.VoiceSettingsScreen

@Composable
@Preview
private fun FullAppShowcasePreview() {
  VoiceTheme {
    FullAppShowcase()
  }
}

@Composable
fun FullAppShowcase(modifier: Modifier = Modifier) {
  var selectedTab by remember { mutableIntStateOf(0) }

  Scaffold(
    bottomBar = {
      VoiceBottomNavBar(
        items = listOf(
          VoiceBottomNavItem(
            label = "Home",
            icon = Icons.Default.Home,
            selected = selectedTab == 0,
            onClick = { selectedTab = 0 },
          ),
          VoiceBottomNavItem(
            label = "Search",
            icon = Icons.Default.Search,
            selected = selectedTab == 1,
            onClick = { selectedTab = 1 },
          ),
          VoiceBottomNavItem(
            label = "Library",
            icon = Icons.Outlined.Description,
            selected = selectedTab == 2,
            onClick = { selectedTab = 2 },
          ),
        ),
      )
    },
  ) { paddingValues ->
    when (selectedTab) {
      0 -> VoiceLibraryScreen(
        books = sampleBooks,
        onSettingsClick = { selectedTab = 2 },
        onBookClick = {},
        modifier = Modifier.padding(paddingValues),
      )
      1 -> VoiceSearchScreen(
        categories = listOf("Fiction", "Non-Fiction", "Mystery", "Science"),
        latestSearches = sampleBooks,
        onSettingsClick = { selectedTab = 2 },
        onCategoryClick = {},
        onBookClick = {},
        modifier = Modifier.padding(paddingValues),
      )
      2 -> VoiceSettingsScreen(
        userName = "John Doe",
        userEmail = "john.doe@example.com",
        onBackClick = { selectedTab = 0 },
        onProfileClick = {},
        onNotificationsClick = {},
        onDataStorageClick = {},
        onSubscriptionClick = {},
        onLinkedAccountClick = {},
        onAboutClick = {},
        onLogoutClick = {},
        modifier = Modifier.padding(paddingValues),
      )
    }
  }
}

private val sampleBooks = listOf(
  BookItem("The Great Gatsby", "F. Scott Fitzgerald", null),
  BookItem("1984", "George Orwell", null),
  BookItem("To Kill a Mockingbird", "Harper Lee", null),
  BookItem("Pride and Prejudice", "Jane Austen", null),
  BookItem("The Catcher in the Rye", "J.D. Salinger", null),
)
