package voice.core.ui.screens

import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.graphics.res.animatedVectorResource
import androidx.compose.animation.graphics.res.rememberAnimatedVectorPainter
import androidx.compose.animation.graphics.vector.AnimatedImageVector
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.PlayArrow
import androidx.compose.material.icons.filled.SkipNext
import androidx.compose.material.icons.filled.SkipPrevious
import androidx.compose.material.icons.filled.Speed
import androidx.compose.ui.draw.BlurredEdgeTreatment
import androidx.compose.ui.draw.blur
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import coil.compose.AsyncImage
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing
import voice.core.ui.VoiceTheme
import voice.core.ui.components.VoiceBottomNavBar
import voice.core.ui.components.VoiceBottomNavItem
import voice.core.ui.components.VoiceIconButton
import voice.core.ui.theme.VoiceColors
import voice.core.ui.theme.VoiceTypography
import voice.core.strings.R as StringsR

@Composable
@Preview
private fun VoicePlayerScreenPreview() {
  VoiceTheme {
    VoicePlayerScreen(
      title = "The Great Gatsby",
      author = "F. Scott Fitzgerald",
      chapterName = "Chapter 1: In My Younger",
      coverUrl = null,
      currentTime = "12:34",
      totalTime = "45:67",
      progress = 0.3f,
      isPlaying = true,
      onPlayPauseClick = {},
      onSkipPreviousClick = {},
      onSkipNextClick = {},
      onSeek = {},
      onBookmarkClick = {},
      onChapterClick = {},
      onSpeedClick = {},
      onDownloadClick = {},
    )
  }
}

@Composable
fun VoicePlayerScreen(
  title: String,
  author: String,
  chapterName: String?,
  coverUrl: String?,
  currentTime: String,
  totalTime: String,
  progress: Float,
  isPlaying: Boolean,
  onPlayPauseClick: () -> Unit,
  onSkipPreviousClick: () -> Unit,
  onSkipNextClick: () -> Unit,
  onSeek: (Float) -> Unit,
  onBookmarkClick: () -> Unit,
  onChapterClick: () -> Unit,
  onSpeedClick: () -> Unit,
  onDownloadClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(
    bottomBar = {
      VoiceBottomNavBar(
        items = listOf(
          VoiceBottomNavItem(
            label = "Home",
            icon = Icons.Default.PlayArrow,
            selected = false,
            onClick = {},
          ),
          VoiceBottomNavItem(
            label = "Search",
            icon = Icons.Default.PlayArrow,
            selected = false,
            onClick = {},
          ),
          VoiceBottomNavItem(
            label = "Library",
            icon = Icons.Default.PlayArrow,
            selected = true,
            onClick = {},
          ),
        ),
      )
    },
  ) { paddingValues ->
    Box(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(paddingValues),
    ) {
      Column(
        modifier = Modifier
          .fillMaxSize()
          .padding(24.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
      ) {
        Spacer(modifier = Modifier.height(32.dp))

        Box(
          modifier = Modifier
            .size(319.dp, 335.dp)
            .clip(RoundedCornerShape(16.dp)),
        ) {
          if (coverUrl != null) {
            AsyncImage(
              model = coverUrl,
              contentDescription = "Book cover",
              modifier = Modifier
                .fillMaxSize()
                .blur(40.dp, edgeTreatment = BlurredEdgeTreatment.Unbounded),
              contentScale = ContentScale.Crop,
            )
          } else {
            Box(
              modifier = Modifier
                .fillMaxSize()
                .background(
                  Brush.verticalGradient(
                    colors = listOf(
                      MaterialTheme.colorScheme.primaryContainer,
                      MaterialTheme.colorScheme.tertiaryContainer,
                    ),
                  ),
                ),
            )
          }

          Box(
            modifier = Modifier
              .fillMaxSize()
              .background(Color.Black.copy(alpha = 0.2f)),
          )

          if (coverUrl != null) {
            AsyncImage(
              model = coverUrl,
              contentDescription = "Book cover",
              modifier = Modifier
                .size(280.dp)
                .clip(RoundedCornerShape(12.dp))
                .align(Alignment.Center),
              contentScale = ContentScale.Crop,
            )
          }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Text(
          text = title,
          style = VoiceTypography.Heading2.semiBold,
          color = MaterialTheme.colorScheme.onBackground,
        )
        Spacer(modifier = Modifier.height(8.dp))
        Text(
          text = author,
          style = VoiceTypography.Body.regular,
          color = MaterialTheme.colorScheme.onSurfaceVariant,
        )
        if (chapterName != null) {
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = chapterName,
            style = VoiceTypography.BodySmall.regular,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }

        Spacer(modifier = Modifier.height(24.dp))

        var sliderPosition by remember { mutableFloatStateOf(progress) }
        Slider(
          value = sliderPosition,
          onValueChange = { sliderPosition = it },
          onValueChangeFinished = { onSeek(sliderPosition) },
          modifier = Modifier.fillMaxWidth(),
          colors = SliderDefaults.colors(
            thumbColor = MaterialTheme.colorScheme.primary,
            activeTrackColor = MaterialTheme.colorScheme.primary,
            inactiveTrackColor = MaterialTheme.colorScheme.surfaceVariant,
          ),
        )

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceBetween,
        ) {
          Text(
            text = currentTime,
            style = VoiceTypography.Small.regular,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
          Text(
            text = totalTime,
            style = VoiceTypography.Small.regular,
            color = MaterialTheme.colorScheme.onSurfaceVariant,
          )
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly,
          verticalAlignment = Alignment.CenterVertically,
        ) {
          IconButton(onClick = onSkipPreviousClick) {
            Icon(
              imageVector = Icons.Default.SkipPrevious,
              contentDescription = "Skip Previous",
              modifier = Modifier.size(32.dp),
              tint = MaterialTheme.colorScheme.onSurface,
            )
          }

          IconButton(
            onClick = onPlayPauseClick,
            modifier = Modifier.size(72.dp),
          ) {
            Icon(
              imageVector = Icons.Default.PlayArrow,
              contentDescription = if (isPlaying) "Pause" else "Play",
              modifier = Modifier.size(48.dp),
              tint = MaterialTheme.colorScheme.primary,
            )
          }

          IconButton(onClick = onSkipNextClick) {
            Icon(
              imageVector = Icons.Default.SkipNext,
              contentDescription = "Skip Next",
              modifier = Modifier.size(32.dp),
              tint = MaterialTheme.colorScheme.onSurface,
            )
          }
        }

        Spacer(modifier = Modifier.height(32.dp))

        Row(
          modifier = Modifier.fillMaxWidth(),
          horizontalArrangement = Arrangement.SpaceEvenly,
        ) {
          IconButton(onClick = onBookmarkClick) {
            Icon(
              imageVector = Icons.Default.Bookmark,
              contentDescription = "Bookmark",
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }

          IconButton(onClick = onChapterClick) {
            Icon(
              imageVector = Icons.Default.PlayArrow,
              contentDescription = "Chapters",
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }

          IconButton(onClick = onSpeedClick) {
            Icon(
              imageVector = Icons.Default.Speed,
              contentDescription = "Speed",
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }

          IconButton(onClick = onDownloadClick) {
            Icon(
              imageVector = Icons.AutoMirrored.Filled.VolumeUp,
              contentDescription = "Download",
              tint = MaterialTheme.colorScheme.onSurfaceVariant,
            )
          }
        }
      }
    }
  }
}
