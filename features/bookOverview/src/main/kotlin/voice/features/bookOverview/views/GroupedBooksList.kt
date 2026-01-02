package voice.features.bookOverview.views

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import kotlinx.collections.immutable.persistentMapOf
import voice.core.data.BookId
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing
import voice.features.bookOverview.overview.BookOverviewCategory
import voice.features.bookOverview.overview.BookOverviewGrouping
import voice.features.bookOverview.overview.BookOverviewItemViewState
import voice.features.bookOverview.overview.BookOverviewLayoutMode
import voice.features.bookOverview.overview.GroupedBooks

@Composable
internal fun GroupedBooksList(
  groupedBooks: List<GroupedBooks>,
  grouping: BookOverviewGrouping,
  layoutMode: BookOverviewLayoutMode,
  onBookClick: (BookId) -> Unit,
  onBookLongClick: (BookId) -> Unit,
  currentlyReading: BookOverviewItemViewState?,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    groupedBooks.forEach { group ->
      var expanded by remember(group.groupName, grouping) { mutableStateOf(true) }
      
      if (grouping != BookOverviewGrouping.COMPLETION_STATUS) {
        GroupHeader(
          groupName = group.groupName,
          bookCount = group.books.values.sumOf { it.size },
          expanded = expanded,
          onExpandChange = { expanded = it },
        )
      }
      
      AnimatedVisibility(visible = expanded || grouping == BookOverviewGrouping.COMPLETION_STATUS) {
        Column {
          group.books.forEach { (category, books) ->
            if (books.isNotEmpty()) {
              CategorySection(
                category = category,
                books = books,
                layoutMode = layoutMode,
                onBookClick = onBookClick,
                onBookLongClick = onBookLongClick,
                currentlyReading = currentlyReading,
                showCategoryHeader = grouping == BookOverviewGrouping.COMPLETION_STATUS,
              )
            }
          }
        }
      }
    }
  }
}

@Composable
private fun GroupHeader(
  groupName: String,
  bookCount: Int,
  expanded: Boolean,
  onExpandChange: (Boolean) -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onExpandChange(!expanded) }
      .padding(horizontal = Spacing.md, vertical = Spacing.sm),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
      contentDescription = if (expanded) "Collapse" else "Expand",
      modifier = Modifier.size(Spacing.lg),
    )
    Spacer(modifier = Modifier.size(Spacing.sm))
    Text(
      text = groupName,
      style = MaterialTheme.typography.titleMedium,
      fontWeight = FontWeight.Bold,
    )
    Spacer(modifier = Modifier.weight(1f))
    Text(
      text = "$bookCount",
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}

@Composable
private fun CategorySection(
  category: BookOverviewCategory,
  books: List<BookOverviewItemViewState>,
  layoutMode: BookOverviewLayoutMode,
  onBookClick: (BookId) -> Unit,
  onBookLongClick: (BookId) -> Unit,
  currentlyReading: BookOverviewItemViewState?,
  showCategoryHeader: Boolean,
  modifier: Modifier = Modifier,
) {
  Column(modifier = modifier) {
    if (showCategoryHeader) {
      Header(category = category)
    }
    
    when (layoutMode) {
      BookOverviewLayoutMode.List -> {
        ListBooks(
          books = persistentMapOf(category to books),
          onBookClick = { onBookClick(it) },
          onBookLongClick = { onBookLongClick(it) },
          showPermissionBugCard = false,
          onPermissionBugCardClick = {},
        )
      }
      BookOverviewLayoutMode.Grid -> {
        GridBooks(
          books = persistentMapOf(category to books),
          onBookClick = { onBookClick(it) },
          onBookLongClick = { onBookLongClick(it) },
          showPermissionBugCard = false,
          onPermissionBugCardClick = {},
        )
      }
    }
  }
}
