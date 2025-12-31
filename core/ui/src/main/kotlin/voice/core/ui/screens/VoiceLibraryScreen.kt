package voice.core.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.ui.tooling.preview.Preview
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing
import voice.core.ui.VoiceTheme
import voice.core.ui.components.VoiceBookCard
import voice.core.ui.components.VoiceBottomNavBar
import voice.core.ui.components.VoiceBottomNavItem
import voice.core.ui.components.VoiceInputField
import voice.core.ui.models.BookItem
import voice.core.ui.theme.VoiceTypography

@Composable
@Preview
private fun VoiceLibraryScreenPreview() {
  VoiceTheme {
    VoiceLibraryScreen(
      books = listOf(
        BookItem("The Great Gatsby", "F. Scott Fitzgerald", null),
        BookItem("1984", "George Orwell", null),
        BookItem("To Kill a Mockingbird", "Harper Lee", null),
      ),
      onSettingsClick = {},
      onBookClick = {},
    )
  }
}

@Composable
fun VoiceLibraryScreen(
  books: List<BookItem>,
  onSettingsClick: () -> Unit,
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
            selected = true,
            onClick = {},
          ),
          VoiceBottomNavItem(
            label = "Search",
            icon = Icons.Default.Settings,
            selected = false,
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

      VoiceInputField(
        value = searchQuery,
        onValueChange = { searchQuery = it },
        placeholder = "Search books...",
        modifier = Modifier.fillMaxWidth(),
      )

      Spacer(modifier = Modifier.height(24.dp))

      Text(
        text = "My Library",
        style = VoiceTypography.Subheading.semiBold,
        color = MaterialTheme.colorScheme.onBackground,
      )

      Spacer(modifier = Modifier.height(16.dp))

      Column(
        verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        books.forEach { book ->
          VoiceBookCard(
            title = book.title,
            author = book.author,
            coverUrl = book.coverUrl,
            onClick = { onBookClick(book) },
          )
        }
      }
    }
  }
}
