package voice.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Star
import androidx.compose.material.icons.outlined.StarOutline
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing

@Composable
fun VoiceRatingBar(
  rating: Int,
  onRatingChange: ((Int) -> Unit)?,
  modifier: Modifier = Modifier,
  maxRating: Int = 5,
  enabled: Boolean = true,
) {
  Row(
    modifier = modifier,
    horizontalArrangement = Arrangement.spacedBy(4.dp),
  ) {
    repeat(maxRating) { index ->
      val isSelected = index < rating
      val starModifier = if (enabled && onRatingChange != null) {
        Modifier
          .size(24.dp)
          .clickable { onRatingChange(index + 1) }
      } else {
        Modifier.size(24.dp)
      }

      Icon(
        imageVector = if (isSelected) Icons.Filled.Star else Icons.Outlined.StarOutline,
        contentDescription = "Star ${index + 1}",
        tint = MaterialTheme.colorScheme.secondary,
        modifier = starModifier,
      )
    }
  }
}
