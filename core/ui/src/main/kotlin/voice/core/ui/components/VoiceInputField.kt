package voice.core.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp

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
