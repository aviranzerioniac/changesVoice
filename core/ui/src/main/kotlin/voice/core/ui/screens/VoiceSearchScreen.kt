package voice.core.ui.screens

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import voice.core.ui.VoiceTheme
import voice.core.ui.components.VoiceBookCardVertical
import voice.core.ui.components.VoiceBottomNavBar
import voice.core.ui.components.VoiceBottomNavItem
import voice.core.ui.components.VoiceButton
import voice.core.ui.components.VoiceButtonStyle
import voice.core.ui.components.VoiceInputField
import voice.core.ui.theme.VoiceTypography

@Composable
@Preview
private fun VoiceSearchScreenPreview() {
  VoiceTheme {
    VoiceSearchScreen(
      categories = listOf("Fiction", "Non-Fiction", "Mystery", "Science"),
      latestSearches = listOf(
        BookItem("The Great Gatsby", "F. Scott Fitzgerald", null),
        BookItem("1984", "George Orwell", null),
        BookItem("To Kill a Mockingbird", "Harper Lee", null),
      ),
      onSettingsClick = {},
      onCategoryClick = {},
      onBookClick = {},
    )
  }
}

@Composable
fun VoiceSearchScreen(
  categories: List<String>,
  latestSearches: List<BookItem>,
  onSettingsClick: () -> Unit,
  onCategoryClick: (String) -> Unit,
  onBookClick: (BookItem) -> Unit,
  modifier: Modifier = Modifier,
) {
  var searchQuery by remember { mutableStateOf("") }

  Scaffold(
    bottomBar = {
      VoiceBottomNavBar(
        items = listOf(
          VoiceBottomNavItem(
            label = "Home",
            icon = Icons.Default.Settings,
            selected = false,
            onClick = {},
          ),
          VoiceBottomNavItem(
            label = "Search",
            icon = Icons.Default.Settings,
            selected = true,
            onClick = {},
          ),
          VoiceBottomNavItem(
            label = "Library",
            icon = Icons.Default.Settings,
            selected = false,
            onClick = {},
          ),
        ),
      )
    },
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
        .padding(20.dp),
    ) {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(
          text = "AudiBooks.",
          style = VoiceTypography.Heading1.bold,
          color = MaterialTheme.colorScheme.onBackground,
        )
        IconButton(onClick = onSettingsClick) {
          Icon(
            imageVector = Icons.Default.Settings,
            contentDescription = "Settings",
            tint = MaterialTheme.colorScheme.onBackground,
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "Explore",
        style = VoiceTypography.Subheading.semiBold,
        color = MaterialTheme.colorScheme.onBackground,
      )

      Spacer(modifier = Modifier.height(16.dp))

      VoiceInputField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = "Search audiobooks...",
        modifier = Modifier.fillMaxWidth(),
      )

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "Categories",
        style = VoiceTypography.Body.semiBold,
        color = MaterialTheme.colorScheme.onBackground,
      )

      Spacer(modifier = Modifier.height(12.dp))

      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        categories.take(4).forEach { category ->
          VoiceButton(
            text = category,
            onClick = { onCategoryClick(category) },
            style = VoiceButtonStyle.Outline,
            modifier = Modifier.weight(1f),
          )
        }
      }

      Spacer(modifier = Modifier.height(32.dp))

      Text(
        text = "Latest Search",
        style = VoiceTypography.Subheading.semiBold,
        color = MaterialTheme.colorScheme.onBackground,
      )

      Spacer(modifier = Modifier.height(16.dp))

      LazyRow(
        horizontalArrangement = Arrangement.spacedBy(12.dp),
        contentPadding = PaddingValues(horizontal = 4.dp),
      ) {
        items(latestSearches) { book ->
          VoiceBookCardVertical(
            title = book.title,
            author = book.author,
            coverUrl = book.coverUrl,
            onClick = { onBookClick(book) },
          )
        }
      }

      Spacer(modifier = Modifier.height(24.dp))
    }
  }
}
