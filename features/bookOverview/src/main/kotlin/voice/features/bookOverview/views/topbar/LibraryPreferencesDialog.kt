package voice.features.bookOverview.views.topbar

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.outlined.ViewList
import androidx.compose.material.icons.filled.ArrowDropDown
import androidx.compose.material.icons.outlined.GridView
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.IconButton
import androidx.compose.material3.DropdownMenu
import androidx.compose.material3.DropdownMenuItem
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
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
// ...existing imports...
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
  onGroupingChange: (BookOverviewGrouping) -> Unit,
  onSortChange: (BookSortOption) -> Unit,
  onFilterChange: (BookFilterOption) -> Unit,
  onLayoutModeChange: (BookOverviewLayoutMode) -> Unit,
  onDismiss: () -> Unit,
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceBetween,
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Text(stringResource(StringsR.string.library_preferences))
        IconButton(onClick = { 
          onLayoutModeChange(
            when (layoutMode) {
              BookOverviewLayoutMode.List -> BookOverviewLayoutMode.Grid
              BookOverviewLayoutMode.Grid -> BookOverviewLayoutMode.List
            }
          )
        }) {
          Icon(
            imageVector = when (layoutMode) {
              BookOverviewLayoutMode.List -> Icons.Outlined.GridView
              BookOverviewLayoutMode.Grid -> Icons.AutoMirrored.Outlined.ViewList
            },
            contentDescription = stringResource(StringsR.string.library_layout_mode),
          )
        }
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .padding(vertical = 8.dp)
      ) {
        CategorySelector(
          selectedGrouping = grouping,
          onGroupingChange = onGroupingChange,
          header = {
            Text(
              text = stringResource(StringsR.string.library_group_heading),
              style = MaterialTheme.typography.titleSmall,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = stringResource(StringsR.string.library_group_subtitle),
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
            )
          },
        )

        Spacer(modifier = Modifier.height(12.dp))

        SortSelector(
          selectedSort = sortOption,
          onSortChange = onSortChange,
          header = {
            Text(
              text = stringResource(StringsR.string.library_sort_heading),
              style = MaterialTheme.typography.titleSmall,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = stringResource(StringsR.string.library_sort_subtitle),
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
            )
          },
        )

        Spacer(modifier = Modifier.height(12.dp))

        // ...existing code...

        FilterSelector(
          selectedFilter = filterOption,
          onFilterChange = onFilterChange,
          header = {
            Text(
              text = stringResource(StringsR.string.library_filter_heading),
              style = MaterialTheme.typography.titleSmall,
              color = MaterialTheme.colorScheme.onSurface,
            )
            Text(
              text = stringResource(StringsR.string.library_filter_subtitle),
              style = MaterialTheme.typography.bodySmall,
              color = MaterialTheme.colorScheme.onSurfaceVariant,
              modifier = Modifier.padding(top = 4.dp, bottom = 8.dp),
            )
          },
        )
      }
    },
    confirmButton = {},
  )
}
// FolderPatternSelector, LayoutSelector, LayoutChip removed