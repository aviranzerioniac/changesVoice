package voice.core.ui

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import voice.core.ui.theme.DarkColorScheme
import voice.core.ui.theme.LightColorScheme
import voice.core.ui.theme.voiceTypography

@Composable
fun VoiceTheme(content: @Composable () -> Unit) {
  MaterialTheme(
    colorScheme = if (isDarkTheme()) {
      DarkColorScheme
    } else {
      LightColorScheme
    },
    typography = voiceTypography,
  ) {
    content()
  }
}
