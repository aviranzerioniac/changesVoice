package voice.core.ui

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import kotlinx.coroutines.Dispatchers
import voice.core.common.rootGraphAs
import voice.core.data.ThemeOption
import voice.core.ui.theme.DarkColorScheme
import voice.core.ui.theme.LightColorScheme
import voice.core.ui.theme.OLEDColorScheme
import voice.core.ui.theme.voiceTypography

@Composable
fun VoiceTheme(content: @Composable () -> Unit) {
  val colorScheme = when (getThemeOption()) {
    ThemeOption.LIGHT -> LightColorScheme
    ThemeOption.DARK -> DarkColorScheme
    ThemeOption.OLED -> OLEDColorScheme
    ThemeOption.SYSTEM -> if (isSystemInDarkTheme()) {
      DarkColorScheme
    } else {
      LightColorScheme
    }
  }
  
  MaterialTheme(
    colorScheme = colorScheme,
    typography = voiceTypography,
  ) {
    content()
  }
}

@Composable
private fun getThemeOption(): ThemeOption {
  val themeStore = remember {
    rootGraphAs<SharedGraph>().themeStore
  }
  return themeStore.data.collectAsState(
    initial = ThemeOption.SYSTEM,
    context = Dispatchers.Unconfined
  ).value
}
