package voice.features.bookOverview.views.topbar

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FilterList
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.res.stringResource
import voice.features.bookOverview.overview.BookFilterOption
import voice.features.bookOverview.overview.BookOverviewGrouping
import voice.features.bookOverview.overview.BookOverviewLayoutMode
import voice.features.bookOverview.overview.BookSortOption
import voice.features.bookOverview.views.BookFolderIcon
import voice.features.bookOverview.views.SettingsIcon
import voice.core.strings.R as StringsR

@Composable
internal fun ColumnScope.TopBarTrailingIcon(
  searchActive: Boolean,
  showAddBookHint: Boolean,
  onBookFolderClick: () -> Unit,
  onSettingsClick: () -> Unit,
  grouping: BookOverviewGrouping,
  sortOption: BookSortOption,
  filterOption: BookFilterOption,
  layoutMode: BookOverviewLayoutMode,
  onGroupingChange: (BookOverviewGrouping) -> Unit,
  onSortChange: (BookSortOption) -> Unit,
  onFilterChange: (BookFilterOption) -> Unit,
  onLayoutModeChange: (BookOverviewLayoutMode) -> Unit,
) {
  var showLibraryPrefsDialog by remember { mutableStateOf(false) }
  
  AnimatedVisibility(
    visible = !searchActive,
    enter = fadeIn(),
    exit = fadeOut(),
  ) {
    Row {
      IconButton(onClick = { showLibraryPrefsDialog = true }) {
        Icon(
          imageVector = Icons.Outlined.FilterList,
          contentDescription = stringResource(StringsR.string.library_preferences),
        )
      }
      BookFolderIcon(withHint = showAddBookHint, onClick = onBookFolderClick)
      SettingsIcon(onSettingsClick)
    }
  }
  
  if (showLibraryPrefsDialog) {
    LibraryPreferencesDialog(
      grouping = grouping,
      layoutMode = layoutMode,
      onGroupingChange = onGroupingChange,
      onSortChange = onSortChange,
      onFilterChange = onFilterChange,
      onLayoutModeChange = onLayoutModepingChange,
      onSortChange = onSortChange,
      onFilterChange = onFilterChange,
      onDismiss = { showLibraryPrefsDialog = false },
    )
  }
}
