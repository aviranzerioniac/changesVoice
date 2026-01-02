package voice.features.bookOverview.editMetadata

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import voice.core.strings.R as StringsR

@Composable
internal fun EditBookMetadataDialog(
  onDismissClick: () -> Unit,
  onConfirmClick: () -> Unit,
  viewState: EditBookMetadataState,
  onUpdateTitle: (String) -> Unit,
  onUpdateAuthor: (String) -> Unit,
  onUpdateNarrator: (String) -> Unit,
  onUpdateSeries: (String) -> Unit,
  onUpdatePart: (String) -> Unit,
  onUpdateGenre: (String) -> Unit,
) {
  AlertDialog(
    onDismissRequest = onDismissClick,
    title = {
      Text(text = stringResource(StringsR.string.edit_book_metadata_title))
    },
    confirmButton = {
      Button(
        onClick = onConfirmClick,
        enabled = viewState.confirmButtonEnabled,
      ) {
        Text(stringResource(id = StringsR.string.dialog_confirm))
      }
    },
    dismissButton = {
      TextButton(onClick = onDismissClick) {
        Text(stringResource(id = StringsR.string.dialog_cancel))
      }
    },
    text = {
      Column(
        modifier = Modifier
          .fillMaxWidth()
          .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(12.dp),
      ) {
        TextField(
          value = viewState.title,
          onValueChange = onUpdateTitle,
          label = { Text(stringResource(StringsR.string.book_title)) },
          modifier = Modifier.fillMaxWidth(),
        )
        
        TextField(
          value = viewState.author,
          onValueChange = onUpdateAuthor,
          label = { Text(stringResource(StringsR.string.book_author)) },
          modifier = Modifier.fillMaxWidth(),
        )
        
        TextField(
          value = viewState.narrator,
          onValueChange = onUpdateNarrator,
          label = { Text(stringResource(StringsR.string.book_narrator)) },
          modifier = Modifier.fillMaxWidth(),
        )
        
        TextField(
          value = viewState.series,
          onValueChange = onUpdateSeries,
          label = { Text(stringResource(StringsR.string.book_series)) },
          modifier = Modifier.fillMaxWidth(),
        )
        
        TextField(
          value = viewState.part,
          onValueChange = onUpdatePart,
          label = { Text(stringResource(StringsR.string.book_part)) },
          modifier = Modifier.fillMaxWidth(),
        )
        
        TextField(
          value = viewState.genre,
          onValueChange = onUpdateGenre,
          label = { Text(stringResource(StringsR.string.book_genre)) },
          modifier = Modifier.fillMaxWidth(),
        )
        
        Text(
          text = stringResource(StringsR.string.edit_book_metadata_hint),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          modifier = Modifier.padding(top = 8.dp),
        )
      }
    },
  )
}
