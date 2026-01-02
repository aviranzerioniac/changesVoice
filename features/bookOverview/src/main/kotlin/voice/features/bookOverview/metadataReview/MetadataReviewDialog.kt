package voice.features.bookOverview.metadataReview

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
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
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import voice.core.metadata.suggester.MetadataSuggestions
import voice.core.metadata.suggester.MetadataSource
import voice.core.strings.R as StringsR

@Composable
fun MetadataReviewDialog(
  suggestions: MetadataSuggestions,
  onAccept: (AcceptedMetadata) -> Unit,
  onDismiss: () -> Unit,
) {
  var author by remember { mutableStateOf(suggestions.authors.firstOrNull()?.value ?: "") }
  var title by remember { mutableStateOf(suggestions.titles.firstOrNull()?.value ?: "") }
  var narrator by remember { mutableStateOf(suggestions.narrators.firstOrNull()?.value ?: "") }
  var series by remember { mutableStateOf(suggestions.series.firstOrNull()?.seriesName ?: "") }

  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Column {
        Text(text = stringResource(StringsR.string.metadata_suggestions_title))
        Text(
          text = stringResource(StringsR.string.metadata_suggestions_subtitle),
          style = MaterialTheme.typography.bodySmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }
    },
    confirmButton = {
      Button(
        onClick = {
          onAccept(
            AcceptedMetadata(
              author = author.takeIf { it.isNotBlank() },
              title = title.takeIf { it.isNotBlank() },
              narrator = narrator.takeIf { it.isNotBlank() },
              series = series.takeIf { it.isNotBlank() },
            ),
          )
        },
        enabled = title.isNotBlank(),
      ) {
        Text(stringResource(StringsR.string.metadata_accept_all))
      }
    },
    dismissButton = {
      TextButton(onClick = onDismiss) {
        Text(stringResource(StringsR.string.metadata_skip))
      }
    },
    text = {
      Column(
        modifier = Modifier.verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.spacedBy(16.dp),
      ) {
        suggestions.authors.firstOrNull()?.let { suggestion ->
          MetadataField(
            label = stringResource(StringsR.string.metadata_suggestion_author),
            value = author,
            onValueChange = { author = it },
            confidence = suggestion.confidence,
            source = suggestion.source.displayName(),
          )
        }

        suggestions.titles.firstOrNull()?.let { suggestion ->
          MetadataField(
            label = stringResource(StringsR.string.metadata_suggestion_title),
            value = title,
            onValueChange = { title = it },
            confidence = suggestion.confidence,
            source = suggestion.source.displayName(),
          )
        }

        suggestions.narrators.firstOrNull()?.let { suggestion ->
          MetadataField(
            label = stringResource(StringsR.string.metadata_suggestion_narrator),
            value = narrator,
            onValueChange = { narrator = it },
            confidence = suggestion.confidence,
            source = suggestion.source.displayName(),
          )
        }

        suggestions.series.firstOrNull()?.let { suggestion ->
          MetadataField(
            label = stringResource(StringsR.string.metadata_suggestion_series),
            value = series,
            onValueChange = { series = it },
            confidence = suggestion.confidence,
            source = suggestion.source.displayName(),
          )
        }
      }
    },
  )
}

@Composable
private fun MetadataField(
  label: String,
  value: String,
  onValueChange: (String) -> Unit,
  confidence: Float,
  source: String,
) {
  Column {
    TextField(
      value = value,
      onValueChange = onValueChange,
      label = { Text(label) },
      modifier = Modifier.fillMaxWidth(),
    )
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(top = 4.dp),
      horizontalArrangement = Arrangement.SpaceBetween,
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Text(
        text = stringResource(
          StringsR.string.metadata_suggestion_confidence,
          (confidence * 100).toInt(),
        ),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
      Text(
        text = stringResource(StringsR.string.metadata_suggestion_source, source),
        style = MaterialTheme.typography.labelSmall,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
      )
    }
  }
}


@Composable
private fun MetadataSource.displayName(): String = when (this) {
  MetadataSource.FILENAME -> stringResource(StringsR.string.metadata_source_filename)
  MetadataSource.FOLDER_STRUCTURE -> stringResource(StringsR.string.metadata_source_folder)
  MetadataSource.EMBEDDED_TAGS -> stringResource(StringsR.string.metadata_source_embedded)
  MetadataSource.USER_INPUT -> stringResource(StringsR.string.metadata_source_user_input)
}

data class AcceptedMetadata(
  val author: String?,
  val title: String?,
  val narrator: String?,
  val series: String?,
)
