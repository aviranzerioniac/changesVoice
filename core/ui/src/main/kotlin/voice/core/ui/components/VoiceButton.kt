package voice.core.ui.components

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.defaultMinSize
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.dp

enum class VoiceButtonStyle {
  Flat,
  Outline,
  Ghost,
}

@Composable
fun VoiceButton(
  text: String,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  style: VoiceButtonStyle = VoiceButtonStyle.Flat,
  icon: ImageVector? = null,
  iconPosition: IconPosition = IconPosition.Left,
  enabled: Boolean = true,
) {
  when (style) {
    VoiceButtonStyle.Flat -> {
      Button(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.primary,
          contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
      ) {
        ButtonContent(text = text, icon = icon, iconPosition = iconPosition)
      }
    }
    VoiceButtonStyle.Outline -> {
      OutlinedButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
          contentColor = MaterialTheme.colorScheme.primary,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
      ) {
        ButtonContent(text = text, icon = icon, iconPosition = iconPosition)
      }
    }
    VoiceButtonStyle.Ghost -> {
      TextButton(
        onClick = onClick,
        modifier = modifier.defaultMinSize(minHeight = 48.dp),
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
          contentColor = MaterialTheme.colorScheme.primary,
        ),
        contentPadding = PaddingValues(horizontal = 24.dp, vertical = 12.dp),
      ) {
        ButtonContent(text = text, icon = icon, iconPosition = iconPosition)
      }
    }
  }
}

@Composable
fun VoiceIconButton(
  icon: ImageVector,
  onClick: () -> Unit,
  modifier: Modifier = Modifier,
  style: VoiceButtonStyle = VoiceButtonStyle.Flat,
  enabled: Boolean = true,
  contentDescription: String? = null,
) {
  when (style) {
    VoiceButtonStyle.Flat -> {
      Button(
        onClick = onClick,
        modifier = modifier.size(48.dp),
        enabled = enabled,
        colors = ButtonDefaults.buttonColors(
          containerColor = MaterialTheme.colorScheme.primary,
          contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        contentPadding = PaddingValues(12.dp),
      ) {
        Icon(
          imageVector = icon,
          contentDescription = contentDescription,
          modifier = Modifier.size(24.dp),
        )
      }
    }
    VoiceButtonStyle.Outline -> {
      OutlinedButton(
        onClick = onClick,
        modifier = modifier.size(48.dp),
        enabled = enabled,
        colors = ButtonDefaults.outlinedButtonColors(
          contentColor = MaterialTheme.colorScheme.primary,
        ),
        border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
        contentPadding = PaddingValues(12.dp),
      ) {
        Icon(
          imageVector = icon,
          contentDescription = contentDescription,
          modifier = Modifier.size(24.dp),
        )
      }
    }
    VoiceButtonStyle.Ghost -> {
      TextButton(
        onClick = onClick,
        modifier = modifier.size(48.dp),
        enabled = enabled,
        colors = ButtonDefaults.textButtonColors(
          contentColor = MaterialTheme.colorScheme.primary,
        ),
        contentPadding = PaddingValues(12.dp),
      ) {
        Icon(
          imageVector = icon,
          contentDescription = contentDescription,
          modifier = Modifier.size(24.dp),
        )
      }
    }
  }
}

@Composable
private fun ButtonContent(
  text: String,
  icon: ImageVector?,
  iconPosition: IconPosition,
) {
  if (icon == null) {
    Text(text = text)
  } else {
    Row(
      verticalAlignment = Alignment.CenterVertically,
    ) {
      if (iconPosition == IconPosition.Left) {
        Icon(
          imageVector = icon,
          contentDescription = null,
          modifier = Modifier.size(20.dp),
        )
        Spacer(modifier = Modifier.width(8.dp))
        Text(text = text)
      } else {
        Text(text = text)
        Spacer(modifier = Modifier.width(8.dp))
        Icon(
          imageVector = icon,
          contentDescription = null,
          modifier = Modifier.size(20.dp),
        )
      }
    }
  }
}

enum class IconPosition {
  Left,
  Right,
}
