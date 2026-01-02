package voice.features.bookOverview.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import voice.features.bookOverview.overview.BookSortOption

@Composable
internal fun SortSelector(
  selectedSort: BookSortOption,
  onSortChange: (BookSortOption) -> Unit,
  modifier: Modifier = Modifier,
) {
  EnumFilterChipRow(
    entries = BookSortOption.entries.toTypedArray(),
    selected = selectedSort,
    onSelect = onSortChange,
    nameRes = { it.nameRes },
    modifier = modifier,
  )
}
