package voice.features.settings.views

import androidx.compose.foundation.clickable
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Palette
import androidx.compose.material3.Icon
import androidx.compose.material3.ListItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import voice.core.data.ThemeOption
import voice.core.strings.R as StringsR

@Composable
internal fun ThemeRow(
  selectedTheme: ThemeOption,
  onThemeClick: () -> Unit,
) {
  ListItem(
    modifier = Modifier.clickable { onThemeClick() },
    leadingContent = {
      Icon(
        imageVector = Icons.Outlined.Palette,
        contentDescription = stringResource(StringsR.string.pref_theme),
      )
    },
    headlineContent = {
      Text(stringResource(StringsR.string.pref_theme))
    },
    supportingContent = {
      val themeText = when (selectedTheme) {
        ThemeOption.SYSTEM -> stringResource(StringsR.string.pref_theme_system)
        ThemeOption.LIGHT -> stringResource(StringsR.string.pref_theme_light)
        ThemeOption.DARK -> stringResource(StringsR.string.pref_theme_dark)
      }
      Text(themeText)
    },
  )
}
