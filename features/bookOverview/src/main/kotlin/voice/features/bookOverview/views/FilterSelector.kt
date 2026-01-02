package voice.features.bookOverview.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import voice.features.bookOverview.overview.BookFilterOption

@Composable
internal fun FilterSelector(
  selectedFilter: BookFilterOption,
  onFilterChange: (BookFilterOption) -> Unit,
  modifier: Modifier = Modifier,
) {
  EnumFilterChipRow(
    entries = BookFilterOption.entries.toTypedArray(),
    selected = selectedFilter,
    onSelect = onFilterChange,
    nameRes = { it.nameRes },
    modifier = modifier,
  )
}
