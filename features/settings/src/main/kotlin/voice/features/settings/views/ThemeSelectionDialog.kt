package voice.features.settings.views

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.RadioButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import voice.core.data.ThemeOption
import voice.core.ui.VoiceCompose
import voice.core.strings.R as StringsR

@Composable
internal fun ThemeSelectionDialog(
  selectedTheme: ThemeOption,
  onThemeSelect: (ThemeOption) -> Unit,
  onDismiss: () -> Unit,
) {
  AlertDialog(
    onDismissRequest = onDismiss,
    title = {
      Text(stringResource(StringsR.string.pref_theme))
    },
    text = {
      Column {
        ThemeOption.entries.forEach { theme ->
          ThemeOptionRow(
            theme = theme,
            selected = selectedTheme == theme,
            onClick = {
              onThemeSelect(theme)
              onDismiss()
            },
          )
        }
      }
    },
    confirmButton = {
      TextButton(onClick = onDismiss) {
        Text(stringResource(StringsR.string.close))
      }
    },
  )
}

@Composable
private fun ThemeOptionRow(
  theme: ThemeOption,
  selected: Boolean,
  onClick: () -> Unit,
) {
  androidx.compose.foundation.layout.Row(
    modifier = Modifier
      .fillMaxWidth()
      .clickable { onClick() }
      .padding(vertical = VoiceCompose.Spacing.small),
    verticalAlignment = androidx.compose.ui.Alignment.CenterVertically,
  ) {
    RadioButton(
      selected = selected,
      onClick = onClick,
    )
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.padding(horizontal = VoiceCompose.Spacing.small))
    Text(
      text = when (theme) {
        ThemeOption.SYSTEM -> stringResource(StringsR.string.pref_theme_system)
        ThemeOption.LIGHT -> stringResource(StringsR.string.pref_theme_light)
        ThemeOption.DARK -> stringResource(StringsR.string.pref_theme_dark)
      },
    )
  }
}
