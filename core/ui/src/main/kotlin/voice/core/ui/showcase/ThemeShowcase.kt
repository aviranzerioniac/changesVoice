package voice.core.ui.showcase

import androidx.compose.ui.tooling.preview.Preview
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing
import voice.core.ui.VoiceTheme
import voice.core.ui.theme.VoiceColors
import voice.core.ui.theme.VoiceTypography

@Composable
@Preview(name = "Light Theme Colors", showBackground = true)
private fun LightThemeColorsPreview() {
  VoiceTheme {
    ColorPaletteShowcase(isDark = false)
  }
}

@Composable
@Preview(name = "Dark Theme Colors", showBackground = true)
private fun DarkThemeColorsPreview() {
  VoiceTheme {
    ColorPaletteShowcase(isDark = true)
  }
}

@Composable
fun ColorPaletteShowcase(
  isDark: Boolean,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = Modifier
      .fillMaxSize()
      .background(MaterialTheme.colorScheme.background)
      .verticalScroll(rememberScrollState())
      .padding(16.dp),
    verticalArrangement = Arrangement.spacedBy(16.dp),
  ) {
    Text(
      text = if (isDark) "Dark Mode Colors" else "Light Mode Colors",
      style = VoiceTypography.Heading1.bold,
      color = MaterialTheme.colorScheme.onBackground,
    )

    HorizontalDivider()

    if (!isDark) {
      ColorSection(
        title = "Primary Blue",
        colors = listOf(
          "5" to VoiceColors.Light.PrimaryBlue.shade5,
          "10" to VoiceColors.Light.PrimaryBlue.shade10,
          "20" to VoiceColors.Light.PrimaryBlue.shade20,
          "30" to VoiceColors.Light.PrimaryBlue.shade30,
          "40" to VoiceColors.Light.PrimaryBlue.shade40,
          "50" to VoiceColors.Light.PrimaryBlue.shade50,
          "60" to VoiceColors.Light.PrimaryBlue.shade60,
          "70" to VoiceColors.Light.PrimaryBlue.shade70,
          "80" to VoiceColors.Light.PrimaryBlue.shade80,
          "90" to VoiceColors.Light.PrimaryBlue.shade90,
          "100" to VoiceColors.Light.PrimaryBlue.shade100,
        ),
      )

      ColorSection(
        title = "Neutral Gray",
        colors = listOf(
          "5" to VoiceColors.Light.NeutralGray.shade5,
          "10" to VoiceColors.Light.NeutralGray.shade10,
          "20" to VoiceColors.Light.NeutralGray.shade20,
          "30" to VoiceColors.Light.NeutralGray.shade30,
          "40" to VoiceColors.Light.NeutralGray.shade40,
          "50" to VoiceColors.Light.NeutralGray.shade50,
          "60" to VoiceColors.Light.NeutralGray.shade60,
          "70" to VoiceColors.Light.NeutralGray.shade70,
          "80" to VoiceColors.Light.NeutralGray.shade80,
          "90" to VoiceColors.Light.NeutralGray.shade90,
          "100" to VoiceColors.Light.NeutralGray.shade100,
        ),
      )

      ColorSection(
        title = "Accent Orange",
        colors = listOf(
          "5" to VoiceColors.Light.AccentOrange.shade5,
          "10" to VoiceColors.Light.AccentOrange.shade10,
          "20" to VoiceColors.Light.AccentOrange.shade20,
          "30" to VoiceColors.Light.AccentOrange.shade30,
          "40" to VoiceColors.Light.AccentOrange.shade40,
          "50" to VoiceColors.Light.AccentOrange.shade50,
          "60" to VoiceColors.Light.AccentOrange.shade60,
          "70" to VoiceColors.Light.AccentOrange.shade70,
          "80" to VoiceColors.Light.AccentOrange.shade80,
          "90" to VoiceColors.Light.AccentOrange.shade90,
          "100" to VoiceColors.Light.AccentOrange.shade100,
        ),
      )
    } else {
      ColorSection(
        title = "Surfaces",
        colors = listOf(
          "Main" to VoiceColors.Dark.Surface.main,
          "Secondary" to VoiceColors.Dark.Surface.secondary,
          "Tertiary" to VoiceColors.Dark.Surface.tertiary,
        ),
      )

      ColorSection(
        title = "Primary Blue (Bright)",
        colors = listOf(
          "5" to VoiceColors.Dark.PrimaryBlueBright.shade5,
          "10" to VoiceColors.Dark.PrimaryBlueBright.shade10,
          "20" to VoiceColors.Dark.PrimaryBlueBright.shade20,
          "30" to VoiceColors.Dark.PrimaryBlueBright.shade30,
          "40" to VoiceColors.Dark.PrimaryBlueBright.shade40,
          "50" to VoiceColors.Dark.PrimaryBlueBright.shade50,
          "60" to VoiceColors.Dark.PrimaryBlueBright.shade60,
          "70" to VoiceColors.Dark.PrimaryBlueBright.shade70,
          "80" to VoiceColors.Dark.PrimaryBlueBright.shade80,
          "90" to VoiceColors.Dark.PrimaryBlueBright.shade90,
          "100" to VoiceColors.Dark.PrimaryBlueBright.shade100,
        ),
      )

      ColorSection(
        title = "Accent Orange (Bright)",
        colors = listOf(
          "40" to VoiceColors.Dark.AccentOrangeBright.shade40,
          "50" to VoiceColors.Dark.AccentOrangeBright.shade50,
          "60" to VoiceColors.Dark.AccentOrangeBright.shade60,
        ),
      )
    }

    HorizontalDivider()

    Text(
      text = "Material 3 Theme Colors",
      style = VoiceTypography.Heading2.semiBold,
      color = MaterialTheme.colorScheme.onBackground,
    )

    ColorSection(
      title = "Theme Colors",
      colors = listOf(
        "primary" to MaterialTheme.colorScheme.primary,
        "onPrimary" to MaterialTheme.colorScheme.onPrimary,
        "secondary" to MaterialTheme.colorScheme.secondary,
        "onSecondary" to MaterialTheme.colorScheme.onSecondary,
        "background" to MaterialTheme.colorScheme.background,
        "onBackground" to MaterialTheme.colorScheme.onBackground,
        "surface" to MaterialTheme.colorScheme.surface,
        "onSurface" to MaterialTheme.colorScheme.onSurface,
      ),
    )
  }
}

@Composable
private fun ColorSection(
  title: String,
  colors: List<Pair<String, androidx.compose.ui.graphics.Color>>,
) {
  Column(
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Text(
      text = title,
      style = VoiceTypography.Subheading.semiBold,
      color = MaterialTheme.colorScheme.onBackground,
    )

    colors.chunked(3).forEach { rowColors ->
      Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.spacedBy(8.dp),
      ) {
        rowColors.forEach { (name, color) ->
          ColorSwatch(
            name = name,
            color = color,
            modifier = Modifier.weight(1f),
          )
        }
        repeat(3 - rowColors.size) {
          Spacer(modifier = Modifier.weight(1f))
        }
      }
    }
  }
}

@Composable
private fun ColorSwatch(
  name: String,
  color: androidx.compose.ui.graphics.Color,
  modifier: Modifier = Modifier,
) {
  Column(
    modifier = modifier,
  ) {
    Surface(
      modifier = Modifier
        .fillMaxWidth()
        .height(60.dp),
      color = color,
    ) {}
    Spacer(modifier = Modifier.height(4.dp))
    Text(
      text = name,
      style = VoiceTypography.Caption.regular,
      color = MaterialTheme.colorScheme.onBackground,
    )
  }
}
