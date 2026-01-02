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

@Composable
internal fun <T : Enum<T>> EnumFilterChipRow(
  entries: Array<T>,
  selected: T,
  onSelect: (T) -> Unit,
  nameRes: (T) -> Int,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .horizontalScroll(rememberScrollState())
      .padding(horizontal = Spacing.md, vertical = Spacing.sm),
    horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
  ) {
    entries.forEach { item ->
      FilterChip(
        selected = selected == item,
        onClick = { onSelect(item) },
        label = {
          Text(text = stringResource(nameRes(item)))
        },
      )
    }
  }
}
