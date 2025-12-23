package voice.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.state.ToggleableState
import androidx.compose.ui.unit.dp

@Composable
fun VoiceCheckbox(
  checked: Boolean,
  onCheckedChange: ((Boolean) -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
) {
  VoiceTriStateCheckbox(
    state = if (checked) ToggleableState.On else ToggleableState.Off,
    onClick = onCheckedChange?.let { { it(!checked) } },
    modifier = modifier,
    enabled = enabled,
  )
}

@Composable
fun VoiceTriStateCheckbox(
  state: ToggleableState,
  onClick: (() -> Unit)?,
  modifier: Modifier = Modifier,
  enabled: Boolean = true,
) {
  val shape = RoundedCornerShape(4.dp)

  val backgroundColor = when {
    !enabled -> MaterialTheme.colorScheme.surfaceVariant
    state != ToggleableState.Off -> MaterialTheme.colorScheme.primary
    else -> Color.Transparent
  }

  val borderColor = when {
    !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    state != ToggleableState.Off -> MaterialTheme.colorScheme.primary
    else -> MaterialTheme.colorScheme.outline
  }

  Box(
    modifier = modifier
      .size(20.dp)
      .clip(shape)
      .background(backgroundColor)
      .border(
        width = 2.dp,
        color = borderColor,
        shape = shape,
      )
      .then(
        if (onClick != null && enabled) {
          Modifier.clickable(onClick = onClick)
        } else {
          Modifier
        },
      ),
    contentAlignment = Alignment.Center,
  ) {
    when (state) {
      ToggleableState.On -> {
        Icon(
          imageVector = Icons.Default.Check,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onPrimary,
          modifier = Modifier.size(16.dp),
        )
      }
      ToggleableState.Indeterminate -> {
        Icon(
          imageVector = Icons.Default.Remove,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onPrimary,
          modifier = Modifier.size(16.dp),
        )
      }
      ToggleableState.Off -> {}
    }
  }
}
