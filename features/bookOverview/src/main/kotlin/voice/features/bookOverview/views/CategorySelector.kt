package voice.features.bookOverview.views

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import voice.features.bookOverview.overview.BookOverviewGrouping

@Composable
internal fun CategorySelector(
  selectedGrouping: BookOverviewGrouping,
  onGroupingChange: (BookOverviewGrouping) -> Unit,
  header: (@Composable () -> Unit)? = null,
  modifier: Modifier = Modifier,
) {
  header?.invoke()
  EnumFilterChipRow(
    entries = BookOverviewGrouping.entries.toTypedArray(),
    selected = selectedGrouping,
    onSelect = onGroupingChange,
    nameRes = { it.nameRes },
    modifier = modifier,
  )
}
