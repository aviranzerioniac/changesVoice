package voice.features.bookOverview.views

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import voice.core.ui.VoiceCompose.Spacing
import voice.features.bookOverview.overview.BookFilterOption

@Composable
internal fun FilterSelector(
  selectedFilter: BookFilterOption,
  onFilterChange: (BookFilterOption) -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .horizontalScroll(rememberScrollState())
      .padding(horizontal = Spacing.md, vertical = Spacing.sm),
    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
  ) {
    BookFilterOption.entries.forEach { filter ->
      FilterChip(
        selected = selectedFilter == filter,
        onClick = { onFilterChange(filter) },
        label = {
          Text(text = stringResource(filter.nameRes))
        },
      )
    }
  }
}
