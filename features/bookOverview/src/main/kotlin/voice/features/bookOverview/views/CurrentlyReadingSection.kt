package voice.features.bookOverview.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import coil.compose.AsyncImage
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing
import voice.features.bookOverview.overview.BookOverviewItemViewState

@Composable
internal fun CurrentlyReadingSection(
  book: BookOverviewItemViewState,
  onBookClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  ElevatedCard(
    modifier = modifier
      .fillMaxWidth()
      .padding(Spacing.md)
      .clickable(onClick = onBookClick),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.primaryContainer,
    ),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(Spacing.md),
      verticalAlignment = Alignment.CenterVertically,
    ) {
      Box(
        modifier = Modifier
          .size(76.dp)
          .clip(MaterialTheme.shapes.medium)
          .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.1f)),
      ) {
        if (book.cover != null) {
          AsyncImage(
            model = book.cover,
            contentDescription = "Book cover",
            modifier = Modifier.size(76.dp),
            contentScale = ContentScale.Crop,
          )
        }
        Box(
          modifier = Modifier
            .align(Alignment.Center)
            .size(32.dp)
            .background(
              MaterialTheme.colorScheme.primary.copy(alpha = 0.9f),
              CircleShape,
            ),
          contentAlignment = Alignment.Center,
        ) {
          Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Playing",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(20.dp),
          )
        }
      }
      
      Spacer(modifier = Modifier.size(Spacing.md))
      
      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = "Currently Reading",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
          fontWeight = FontWeight.Medium,
        )
        Spacer(modifier = Modifier.size(4.dp))
        Text(
          text = book.name,
          style = MaterialTheme.typography.titleMedium,
          color = MaterialTheme.colorScheme.onPrimaryContainer,
          fontWeight = FontWeight.Bold,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
        )
        if (book.author != null) {
          Spacer(modifier = Modifier.size(2.dp))
          Text(
            text = book.author,
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.8f),
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
        }
        Spacer(modifier = Modifier.size(4.dp))
        Text(
          text = "${book.progress}% • ${book.timeLeft}",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
        )
      }
    }
  }
}
