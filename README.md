# Voice

![CI](https://github.com/VoiceAudiobook/Voice/actions/workflows/ci.yml/badge.svg?branch=main) <a href="https://hosted.weblate.org/engage/voice/">
<img src="https://hosted.weblate.org/widgets/voice/-/svg-badge.svg" alt="Translation status" />
</a>

<a href="https://play.google.com/store/apps/details?id=de.ph1b.audiobook"><img src="https://raw.githubusercontent.com/VoiceAudiobook/Voice/main/fastlane/metadata/android/en-US/images/featureGraphic.jpeg" width="600" ></a>

**Voice** is a minimalistic audiobook player for Android, built for reliability and simplicity.

<a href="https://f-droid.org/packages/de.ph1b.audiobook/">
  <img alt="Get it on F-Droid" height="80" src="https://f-droid.org/badge/get-it-on.png" />
</a>
<a href="https://play.google.com/store/apps/details?id=de.ph1b.audiobook">
  <img alt="Get it on Google Play" height="80" src="https://play.google.com/intl/en_us/badges/images/generic/en_badge_web_generic.png" />
</a>

## Architecture

- **Kotlin** with Jetpack Compose UI
- **Multi-module Gradle** structure (see [architecture docs](docs/architecture.md))
- **Material3** design system with light/dark mode support

## Design System

The app features a comprehensive design system with:

**Colors**: 33+ definitions across light/dark modes
- Light: Primary Blue (#4838D1), Accent Orange (#F77A55), Neutral grays
- Dark: Primary Blue Bright (#7B6AF0), Accent Orange Bright (#FF9975), custom surfaces

**Typography**: 40 styles (8 sizes × 5 weights) using Poppins font
- Sizes: Caption (10px) to Display (48px)
- Weights: Light, Regular, Medium, SemiBold, Bold

**Components** in `core/ui/components`:
- `VoiceButton` - Flat, Outline, Ghost styles
- `VoiceInputField` - All states (normal, active, disabled, readonly)
- `VoiceCheckbox`, `VoiceRatingBar`, `VoiceBottomNavBar`, `VoiceCard`

**Screen Templates** in `core/ui/screens`:
- `VoicePlayerScreen`, `VoiceLibraryScreen`, `VoiceSettingsScreen`, `VoiceSearchScreen`

### Usage

```kotlin
import voice.core.ui.VoiceTheme

@Composable
fun MyScreen() {
  VoiceTheme {
    Text("Hello", color = MaterialTheme.colorScheme.primary)
    VoiceButton(text = "Save", onClick = { }, style = VoiceButtonStyle.Flat)
  }
}
```

All colors use `MaterialTheme.colorScheme.*` and typography uses `VoiceTypography.*` from `core/ui/theme/`.

## Development

**Build**: `./gradlew :app:assemblePlayDebug`  
**Tests**: `./gradlew voiceUnitTest`  
**New Module**: `./scripts/new_module.kts :features:<name>`

See [development docs](docs/development.md) for detailed setup.

## Contributing

Translations are managed on [Weblate](https://hosted.weblate.org/engage/voice/). For code contributions, see [CONTRIBUTING.md](CONTRIBUTING.md).

## License

Licensed under [GNU GPLv3](LICENSE.md). By contributing, you agree to the same terms.
