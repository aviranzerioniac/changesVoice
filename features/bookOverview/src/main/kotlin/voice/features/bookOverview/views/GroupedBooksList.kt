package voice.features.bookOverview.views

import androidx.compose.animation.animateContentSize
import androidx.compose.foundation.clickable
import androidx.compose.foundation.gestures.FlingBehavior
import androidx.compose.foundation.gestures.ScrollScope
import androidx.compose.foundation.gestures.ScrollableDefaults
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandLess
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.datastore.core.DataStore
import kotlinx.coroutines.launch
import voice.core.data.BookId
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
  recentlyStarted: List<BookOverviewItemViewState>,
  expansionStore: DataStore<Set<String>>? = null,
  modifier: Modifier = Modifier,
) {
  val scope = rememberCoroutineScope()
  val initialGroupNames = remember(groupedBooks) { groupedBooks.map { it.groupName }.toSet() }

  val baseFling = ScrollableDefaults.flingBehavior()
  val gentleFling = remember(baseFling) {
    object : FlingBehavior {
      override suspend fun ScrollScope.performFling(initialVelocity: Float): Float {
        // Scale down the initial velocity to soften abrupt stops.
        return with(baseFling) { this@performFling.performFling(initialVelocity * 0.7f) }
      }
    }
  }

  var localExpandedGroups by remember { mutableStateOf(initialGroupNames) }
  LaunchedEffect(groupedBooks) {
    val currentNames = groupedBooks.map { it.groupName }.toSet()
    localExpandedGroups = (localExpandedGroups intersect currentNames) + (currentNames - localExpandedGroups)
  }

  val expandedGroups = expansionStore
    ?.data
    ?.collectAsState(initial = initialGroupNames)
    ?.value
    ?: localExpandedGroups

  fun toggleGroup(groupName: String) {
    if (expansionStore != null) {
      scope.launch {
        expansionStore.updateData { current ->
          if (current.contains(groupName)) current - groupName else current + groupName
        }
      }
    } else {
      localExpandedGroups = if (expandedGroups.contains(groupName)) {
        expandedGroups - groupName
      } else {
        expandedGroups + groupName
      }
    }
  }

  val gridColumns = if (layoutMode == BookOverviewLayoutMode.Grid) gridColumnCount() else 1

  LazyColumn(
    modifier = modifier,
    contentPadding = PaddingValues(horizontal = Spacing.md, vertical = Spacing.sm),
    verticalArrangement = Arrangement.spacedBy(Spacing.sm),
    flingBehavior = gentleFling,
  ) {
    currentlyReading?.let { current ->
      item(
        key = "currently_reading",
        contentType = "currently_reading",
      ) {
        CurrentlyReadingSection(
          book = current,
          onBookClick = { onBookClick(current.id) },
          modifier = Modifier.animateContentSize(),
        )
      }
    }

    if (recentlyStarted.isNotEmpty()) {
      item(
        key = "recently_started",
        contentType = "recently_started",
      ) {
        RecentlyStartedRow(
          books = recentlyStarted,
          onBookClick = onBookClick,
          modifier = Modifier.animateContentSize(),
        )
      }
    }

    groupedBooks.forEach { group ->
      val visibleBooksByCategory = group.books.mapValues { (_, books) ->
        books.filter { book -> currentlyReading?.id != book.id }
      }
      val categoriesWithBooks = visibleBooksByCategory.filterValues { books -> books.isNotEmpty() }
      val groupBookCount = categoriesWithBooks.values.sumOf { it.size }
      val isExpanded = expandedGroups.contains(group.groupName)

      item(
        key = "group_header_${group.groupName}",
        contentType = "group_header",
      ) {
        GroupHeader(
          groupName = group.groupName,
          bookCount = groupBookCount,
          expanded = isExpanded,
          onToggle = { toggleGroup(group.groupName) },
          modifier = Modifier.animateContentSize(),
        )
      }

      if (isExpanded && groupBookCount > 0) {
        categoriesWithBooks
          .toList()
          .sortedBy { (category, _) -> category.ordinal }
          .forEach { (category, books) ->
            item(
              key = "category_header_${group.groupName}_${category.name}",
              contentType = "category_header",
            ) {
              CategoryHeader(
                category = category,
                modifier = Modifier.animateContentSize(),
              )
            }

            if (layoutMode == BookOverviewLayoutMode.Grid) {
              val rows = books.chunked(gridColumns)
              items(
                count = rows.size,
                key = { rowIndex -> "grid_${group.groupName}_${category.name}_row_${rowIndex}" },
                contentType = { "grid_row" },
              ) { rowIndex ->
                val rowBooks = rows[rowIndex]
                Row(
                  modifier = Modifier
                    .fillMaxWidth(),
                  horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
                ) {
                  rowBooks.forEach { book ->
                    Box(modifier = Modifier.weight(1f)) {
                      GridBook(
                        book = book,
                        onBookClick = onBookClick,
                        onBookLongClick = onBookLongClick,
                      )
                    }
                  }
                  if (rowBooks.size < gridColumns) {
                    repeat(gridColumns - rowBooks.size) {
                      Spacer(modifier = Modifier.weight(1f))
                    }
                  }
                }
              }
            } else {
              items(
                items = books,
                key = { book -> "list_${group.groupName}_${category.name}_${book.id.value}" },
                contentType = { "list_item" },
              ) { book ->
                ListBookRow(
                  book = book,
                  onBookClick = onBookClick,
                  onBookLongClick = onBookLongClick,
                  modifier = Modifier
                    .fillMaxWidth(),
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
  onToggle: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .clickable { onToggle() }
      .padding(horizontal = Spacing.md, vertical = Spacing.sm),
    verticalAlignment = Alignment.CenterVertically,
    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
  ) {
    Icon(
      imageVector = if (expanded) Icons.Default.ExpandLess else Icons.Default.ExpandMore,
      contentDescription = if (expanded) "Collapse" else "Expand",
      modifier = Modifier.size(Spacing.lg),
    )
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
private fun CategoryHeader(
  category: BookOverviewCategory,
  modifier: Modifier = Modifier,
) {
  Text(
    text = stringResource(id = category.nameRes),
    style = MaterialTheme.typography.labelMedium,
    color = MaterialTheme.colorScheme.onSurfaceVariant,
    modifier = modifier
      .fillMaxWidth()
      .padding(bottom = Spacing.sm),
  )
}
