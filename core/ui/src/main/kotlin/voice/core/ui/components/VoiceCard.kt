package voice.core.ui.components

import coil.compose.AsyncImage
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing
import voice.core.ui.theme.VoiceTypography

@Composable
fun VoiceBookCard(
  title: String,
  author: String,
  coverUrl: String?,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Row(
    modifier = modifier
      .fillMaxWidth()
      .height(104.dp)
      .clip(RoundedCornerShape(8.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .clickable(onClick = onClick)
      .padding(12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Box(
      modifier = Modifier
        .size(80.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colorScheme.primaryContainer),
    ) {
      if (coverUrl != null) {
        AsyncImage(
          model = coverUrl,
          contentDescription = "Book cover",
          modifier = Modifier.size(80.dp),
          contentScale = ContentScale.Crop,
        )
      }
    }

    Spacer(modifier = Modifier.width(12.dp))

    Column(
      modifier = Modifier.weight(1f),
      verticalArrangement = Arrangement.Center,
    ) {
      Text(
        text = title,
        style = VoiceTypography.Body.medium,
        color = MaterialTheme.colorScheme.onSurface,
        maxLines = 2,
        overflow = TextOverflow.Ellipsis,
      )
      Spacer(modifier = Modifier.height(4.dp))
      Text(
        text = author,
        style = VoiceTypography.Small.regular,
        color = MaterialTheme.colorScheme.onSurfaceVariant,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
      )
    }
  }
}

@Composable
fun VoiceBookCardVertical(
  title: String,
  author: String,
  coverUrl: String?,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier
      .width(160.dp)
      .clip(RoundedCornerShape(8.dp))
      .background(MaterialTheme.colorScheme.surfaceVariant)
      .clickable(onClick = onClick)
      .padding(8.dp),
    horizontalAlignment = Alignment.CenterHorizontally,
  ) {
    Box(
      modifier = Modifier
        .fillMaxWidth()
        .height(196.dp)
        .clip(RoundedCornerShape(4.dp))
        .background(MaterialTheme.colorScheme.primaryContainer),
    ) {
      if (coverUrl != null) {
        AsyncImage(
          model = coverUrl,
          contentDescription = "Book cover",
          modifier = Modifier.fillMaxWidth(),
          contentScale = ContentScale.Crop,
        )
      }
    }

    Spacer(modifier = Modifier.height(8.dp))

    Text(
      text = title,
      style = VoiceTypography.BodySmall.medium,
      color = MaterialTheme.colorScheme.onSurface,
      maxLines = 2,
      overflow = TextOverflow.Ellipsis,
    )
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = author,
      style = VoiceTypography.Small.regular,
      color = MaterialTheme.colorScheme.onSurfaceVariant,
      maxLines = 1,
      overflow = TextOverflow.Ellipsis,
    )
  }
}
