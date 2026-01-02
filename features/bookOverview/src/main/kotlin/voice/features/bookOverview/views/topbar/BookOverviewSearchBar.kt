package voice.features.bookOverview.views.topbar

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.SearchBar
import androidx.compose.material3.SearchBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import voice.core.data.BookId
import voice.features.bookOverview.overview.BookFilterOption
import voice.features.bookOverview.overview.BookOverviewGrouping
import voice.features.bookOverview.overview.BookOverviewLayoutMode
import voice.features.bookOverview.overview.BookSortOption
import voice.features.bookOverview.search.BookSearchContent
import voice.features.bookOverview.search.BookSearchViewState

@Composable
internal fun ColumnScope.BookOverviewSearchBar(
  horizontalPadding: Dp,
  onQueryChange: (String) -> Unit,
  onActiveChange: (Boolean) -> Unit,
  onBookFolderClick: () -> Unit,
  onSettingsClick: () -> Unit,
  onSearchBookClick: (BookId) -> Unit,
  searchActive: Boolean,
  showAddBookHint: Boolean,
  searchViewState: BookSearchViewState,
  grouping: BookOverviewGrouping,
  sortOption: BookSortOption,
  filterOption: BookFilterOption,
  layoutMode: BookOverviewLayoutMode,
  onGroupingChange: (BookOverviewGrouping) -> Unit,
  onSortChange: (BookSortOption) -> Unit,
  onFilterChange: (BookFilterOption) -> Unit,
  onLayoutModeChange: (BookOverviewLayoutMode) -> Unit,
) {
  SearchBar(
    inputField = {
      SearchBarDefaults.InputField(
        query = if (searchActive) {
          searchViewState.query
        } else {
          ""
        },
        onQueryChange = onQueryChange,
        onSearch = onQueryChange,
        expanded = searchActive,
        onExpandedChange = onActiveChange,
        leadingIcon = {
          TopBarLeadingIcon(
            searchActive = searchActive,
            onActiveChange = onActiveChange,
          )
        },
        trailingIcon = {
          TopBarTrailingIcon(
            searchActive = searchActive,
            showAddBookHint = showAddBookHint,
            onBookFolderClick = onBookFolderClick,
            onSettingsClick = onSettingsClick,
            grouping = grouping,
            sortOption = sortOption,
            filterOption = filterOption,
            layoutMode = layoutMode,
            onGroupingChange = onGroupingChange,
            onSortChange = onSortChange,
            onFilterChange = onFilterChange,
            onLayoutModeChange = onLayoutModeChange,
          )
        },
      )
    },
    expanded = searchActive,
    onExpandedChange = onActiveChange,
    modifier = Modifier
      .fillMaxWidth()
      .padding(horizontal = horizontalPadding),
    content = {
      BookSearchContent(
        viewState = searchViewState,
        contentPadding = PaddingValues(),
        onQueryChange = onQueryChange,
        onBookClick = onSearchBookClick,
      )
    },
  )
}
