package voice.features.bookOverview.views.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import voice.core.metadata.suggester.FolderStructurePattern
import voice.features.bookOverview.overview.BookFilterOption
import voice.features.bookOverview.overview.BookOverviewGrouping
import voice.features.bookOverview.overview.BookOverviewLayoutMode
import voice.features.bookOverview.overview.BookSortOption
import voice.features.bookOverview.views.CategorySelector
import voice.features.bookOverview.views.FilterSelector
import voice.features.bookOverview.views.SortSelector
import voice.core.strings.R as StringsR

@Composable
internal fun LibraryPreferencesDialog(
  grouping: BookOverviewGrouping,
  sortOption: BookSortOption,
  filterOption: BookFilterOption,
  layoutMode: BookOverviewLayoutMode,
  folderPattern: FolderStructurePattern,
  onGroupingChange: (BookOverviewGrouping) -> Unit,
  onSortChange: (BookSortOption) -> Unit,
  onFilterChange: (BookFilterOption) -> Unit,
  onLayoutModeChange: (BookOverviewLayoutMode) -> Unit,
  onFolderPatternChange: (FolderStructurePattern) -> Unit,
  onDismiss: () -> Unit,
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(stringResource(StringsR.string.library_preferences))
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      ) {
        Text(
          text = stringResource(StringsR.string.group_by),
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(8.dp))
        CategorySelector(
          selectedGrouping = grouping,
          onGroupingChange = onGroupingChange,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = stringResource(StringsR.string.sort_by),
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(8.dp))
        SortSelector(
          selectedSort = sortOption,
          onSortChange = onSortChange,
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
          text = stringResource(StringsR.string.filter),
          style = MaterialTheme.typography.labelMedium,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        Spacer(modifier = Modifier.height(8.dp))
        FilterSelector(
          selectedFilter = filterOption,

        Spacer(modifier = Modifier.height(16.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          verticalAlignment = Alignment.CenterVertically,
        ) {
          Text(
            text = stringResource(StringsR.string.pref_use_grid),
            style = MaterialTheme.typography.bodyMedium,
            color = MaterialTheme.colorScheme.onSurface,
            modifier = Modifier.weight(1f),
          )
          Switch(
            checked = layoutMode == BookOverviewLayoutMode.Grid,
            onCheckedChange = { isGrid ->
              onLayoutModeChange(
                if (isGrid) BookOverviewLayoutMode.Grid else BookOverviewLayoutMode.List
              )
            },
          )
        }
          onFilterChange = onFilterChange,
        )
      }
    },
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text(stringResource(StringsR.string.dialog_confirm))
      }
    },
  )
}
@Composable
private fun FolderPatternSelector(
  selectedPattern: FolderStructurePattern,
  onPatternChange: (FolderStructurePattern) -> Unit,
) {
  var expanded by remember { mutableStateOf(false) }
  
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { expanded = true }
      .padding(vertical = 8.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Text(
      text = selectedPattern.displayName(),
      style = MaterialTheme.typography.bodyMedium,
      color = MaterialTheme.colorScheme.onSurface,
      modifier = Modifier.weight(1f),
    )
    Icon(
      imageVector = Icons.Default.ArrowDropDown,
      contentDescription = null,
      tint = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
  
  DropdownMenu(
    expanded = expanded,
    onDismissRequest = { expanded = false },
  ) {
    FolderStructurePattern.entries.forEach { pattern ->
      DropdownMenuItem(
        text = { Text(pattern.displayName()) },
        onClick = {
          onPatternChange(pattern)
          expanded = false
        },
      )
    }
  }
}

@Composable
private fun FolderStructurePattern.displayName(): String = when (this) {
  FolderStructurePattern.BOOK_ONLY -> stringResource(StringsR.string.folder_pattern_book_only)
  FolderStructurePattern.AUTHOR_BOOK -> stringResource(StringsR.string.folder_pattern_author_book)
  FolderStructurePattern.SERIES_BOOK -> stringResource(StringsR.string.folder_pattern_series_book)
  FolderStructurePattern.AUTHOR_SERIES_BOOK -> stringResource(StringsR.string.folder_pattern_author_series_book)
}