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
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing
import voice.features.bookOverview.overview.BookOverviewGrouping

@Composable
internal fun CategorySelector(
  selectedGrouping: BookOverviewGrouping,
  onGroupingChange: (BookOverviewGrouping) -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .horizontalScroll(rememberScrollState())
      .padding(horizontal = Spacing.md, vertical = Spacing.sm),
    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
  ) {
    BookOverviewGrouping.entries.forEach { grouping ->
      FilterChip(
        selected = selectedGrouping == grouping,
        onClick = { onGroupingChange(grouping) },
        label = {
          Text(text = stringResource(grouping.nameRes))
        },
      )
    }
  }
}
