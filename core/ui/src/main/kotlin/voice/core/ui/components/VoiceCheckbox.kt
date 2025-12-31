package voice.core.ui.components

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Check
import androidx.compose.material.icons.filled.Remove
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Sizes

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
