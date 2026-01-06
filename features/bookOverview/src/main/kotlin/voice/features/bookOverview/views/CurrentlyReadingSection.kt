package voice.features.bookOverview.views

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import coil.request.ImageRequest
import voice.core.data.BookId
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing
import voice.core.strings.R as StringsR
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
        AsyncImage(
          model = ImageRequest.Builder(LocalContext.current)
            .data(book.cover?.file)
            .crossfade(false)
            .build(),
          contentDescription = "Book cover",
          modifier = Modifier.size(76.dp),
          contentScale = ContentScale.Crop,
          placeholder = painterResource(id = voice.core.ui.R.drawable.album_art),
          error = painterResource(id = voice.core.ui.R.drawable.album_art),
        )
        Box(
          modifier = Modifier
            .align(Alignment.BottomEnd)
            .padding(4.dp)
            .size(24.dp)
            .background(
              MaterialTheme.colorScheme.primary.copy(alpha = 0.95f),
              CircleShape,
            ),
          contentAlignment = Alignment.Center,
        ) {
          Icon(
            imageVector = Icons.Default.PlayArrow,
            contentDescription = "Playing",
            tint = MaterialTheme.colorScheme.onPrimary,
            modifier = Modifier.size(16.dp),
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
          text = "${(book.progress * 100).toInt()}% • ${book.remainingTime}",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onPrimaryContainer.copy(alpha = 0.7f),
        )
      }
    }
  }
}

@Composable
internal fun RecentlyStartedRow(
  books: List<BookOverviewItemViewState>,
  onBookClick: (BookId) -> Unit,
  modifier: Modifier = Modifier,
) {
  if (books.isEmpty()) return

  Column(
    modifier = modifier
      .fillMaxWidth()
      .padding(horizontal = Spacing.md, vertical = Spacing.sm),
    verticalArrangement = Arrangement.spacedBy(Spacing.sm),
  ) {
    Text(
      text = stringResource(id = StringsR.string.recently_started),
      style = MaterialTheme.typography.labelMedium,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      fontWeight = FontWeight.Medium,
    )

    LazyRow(
      horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
      contentPadding = PaddingValues(end = Spacing.md),
    ) {
      items(
        items = books,
        key = { book -> book.id.value },
      ) { book ->
        RecentlyStartedCard(
          book = book,
          onBookClick = { onBookClick(book.id) },
        )
      }
    }
  }
}

@Composable
private fun RecentlyStartedCard(
  book: BookOverviewItemViewState,
  onBookClick: () -> Unit,
) {
  ElevatedCard(
    modifier = Modifier
      .width(180.dp)
      .height(96.dp)
      .clickable(onClick = onBookClick),
    colors = CardDefaults.cardColors(
      containerColor = MaterialTheme.colorScheme.surfaceVariant,
    ),
  ) {
    Row(
      modifier = Modifier
        .fillMaxWidth()
        .padding(Spacing.md),
      verticalAlignment = Alignment.CenterVertically,
      horizontalArrangement = Arrangement.spacedBy(Spacing.sm),
    ) {
      AsyncImage(
        model = ImageRequest.Builder(LocalContext.current)
          .data(book.cover?.file)
          .crossfade(false)
          .build(),
        contentDescription = "Book cover",
        modifier = Modifier
          .size(56.dp)
          .clip(MaterialTheme.shapes.medium)
          .background(MaterialTheme.colorScheme.primary.copy(alpha = 0.08f)),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(id = voice.core.ui.R.drawable.album_art),
        error = painterResource(id = voice.core.ui.R.drawable.album_art),
      )

      Column(modifier = Modifier.weight(1f)) {
        Text(
          text = book.name,
          style = MaterialTheme.typography.bodyMedium,
          color = MaterialTheme.colorScheme.onSurface,
          fontWeight = FontWeight.SemiBold,
          maxLines = 2,
          overflow = TextOverflow.Ellipsis,
        )
        if (book.author != null) {
          Spacer(modifier = Modifier.size(4.dp))
          Text(
            text = book.author,
            style = MaterialTheme.typography.labelSmall,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
          )
        }
        Spacer(modifier = Modifier.size(6.dp))
        Text(
          text = "${(book.progress * 100).toInt()}% • ${book.remainingTime}",
          style = MaterialTheme.typography.labelSmall,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
          maxLines = 1,
          overflow = TextOverflow.Ellipsis,
        )
      }
    }
  }
}
