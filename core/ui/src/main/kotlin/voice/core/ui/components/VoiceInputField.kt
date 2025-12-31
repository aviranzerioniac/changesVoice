package voice.core.ui.components

import androidx.compose.ui.text.input.VisualTransformation
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing

@Composable
fun VoiceInputField(
  value: String,
  onValueChange: (String) -> Unit,
  modifier: Modifier = Modifier,
  placeholder: String = "",
  enabled: Boolean = true,
  readOnly: Boolean = false,
  singleLine: Boolean = true,
  keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
  keyboardActions: KeyboardActions = KeyboardActions.Default,
  visualTransformation: VisualTransformation = VisualTransformation.None,
) {
  val backgroundColor = if (readOnly || !enabled) {
    MaterialTheme.colorScheme.surfaceVariant.copy(alpha = 0.5f)
  } else {
    MaterialTheme.colorScheme.surfaceVariant
  }

  val borderColor = when {
    !enabled -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    readOnly -> MaterialTheme.colorScheme.outline.copy(alpha = 0.5f)
    value.isNotEmpty() -> MaterialTheme.colorScheme.primary
    else -> MaterialTheme.colorScheme.outline.copy(alpha = 0.3f)
  }

  val textColor = when {
    !enabled -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.38f)
    readOnly -> MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
    else -> MaterialTheme.colorScheme.onSurface
  }

  BasicTextField(
    value = value,
    onValueChange = onValueChange,
    modifier = modifier,
    enabled = enabled,
    readOnly = readOnly,
    textStyle = MaterialTheme.typography.bodyLarge.copy(color = textColor),
    keyboardOptions = keyboardOptions,
    keyboardActions = keyboardActions,
    singleLine = singleLine,
    visualTransformation = visualTransformation,
    cursorBrush = SolidColor(MaterialTheme.colorScheme.primary),
    decorationBox = { innerTextField ->
      Box(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .background(backgroundColor)
          .border(
            width = 1.dp,
            color = borderColor,
            shape = RoundedCornerShape(8.dp),
          )
          .padding(horizontal = 24.dp, vertical = 16.dp),
      ) {
        if (value.isEmpty()) {
          Text(
            text = placeholder,
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.onSurfaceVariant.copy(alpha = 0.6f),
          )
        }
        innerTextField()
      }
    },
  )
}
