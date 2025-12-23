# Voice Design System

Complete design system for the Voice audiobook player with comprehensive light and dark mode support.

## Overview

The Voice design system provides a cohesive set of colors, typography, and UI components following Material Design 3 principles with custom branding.

## Colors

### Light Mode

#### Primary Blue
- `shade5` to `shade100` (11 shades)
- Primary action color: `shade50` (#4838D1)
- Used for buttons, links, active states

#### Neutral Gray
- `shade5` to `shade100` (11 shades)
- Text colors: `shade60` (secondary), `shade100` (primary)
- Backgrounds: `shade5` for surfaces

#### Accent Orange
- `shade5` to `shade100` (11 shades)
- Rating stars: `shade50` (#F77A55)
- Error states and highlights

### Dark Mode

#### Surfaces
- `main` (#0F0F1D) - Primary background
- `secondary` (#1A1A2E) - Cards, inputs
- `tertiary` (#252541) - Elevated surfaces

#### Primary Blue (Bright)
- `shade5` to `shade100` (11 shades)
- Primary action color: `shade60` (#7B6AF0)
- Adjusted for dark backgrounds

#### Text Colors
- `primary` (#FFFFFF) - Main text
- `secondary` (#B8B8C7) - Secondary text
- `tertiary` (#9292A2) - Tertiary text

#### Accent Orange (Bright)
- `shade40`, `shade50`, `shade60`
- Rating stars: `shade50` (#FF9975)

## Typography

All text styles use the Poppins font family (falls back to system default if not available).

### Sizes & Line Heights

| Name | Size | Line Height | Weights Available |
|------|------|-------------|-------------------|
| Caption | 10px | 15px | Light, Regular, Medium, SemiBold, Bold |
| Small | 12px | 18px | Light, Regular, Medium, SemiBold, Bold |
| Body Small | 14px | 21px | Light, Regular, Medium, SemiBold, Bold |
| Body | 16px | 24px | Light, Regular, Medium, SemiBold, Bold |
| Subheading | 20px | 30px | Light, Regular, Medium, SemiBold, Bold |
| Heading 2 | 24px | 36px | Light, Regular, Medium, SemiBold, Bold |
| Heading 1 | 32px | 48px | Light, Regular, Medium, SemiBold, Bold |
| Display | 48px | 72px | Light, Regular, Medium, SemiBold, Bold |

### Usage

```kotlin
import voice.core.ui.theme.VoiceTypography

Text(
  text = "Hello World",
  style = VoiceTypography.Body.regular
)
```

## Components

### VoiceButton

Three styles: Flat, Outline, Ghost

```kotlin
VoiceButton(
  text = "Click Me",
  onClick = { },
  style = VoiceButtonStyle.Flat,
  icon = Icons.Default.Add,
  iconPosition = IconPosition.Left,
  enabled = true
)

VoiceIconButton(
  icon = Icons.Default.Star,
  onClick = { },
  style = VoiceButtonStyle.Outline
)
```

### VoiceInputField

Five states: Normal, Active, With Value, Disabled, Read Only

```kotlin
VoiceInputField(
  value = text,
  onValueChange = { text = it },
  placeholder = "Enter text...",
  enabled = true,
  readOnly = false
)
```

### VoiceCheckbox

Three states: Unchecked, Checked, Indeterminate

```kotlin
VoiceCheckbox(
  checked = isChecked,
  onCheckedChange = { isChecked = it },
  enabled = true
)
```

### VoiceRatingBar

5-star rating component

```kotlin
VoiceRatingBar(
  rating = rating,
  onRatingChange = { rating = it },
  maxRating = 5,
  enabled = true
)
```

### VoiceBottomNavBar

Bottom navigation with 3 tabs

```kotlin
VoiceBottomNavBar(
  items = listOf(
    VoiceBottomNavItem(
      label = "Home",
      icon = Icons.Default.Home,
      selected = true,
      onClick = { }
    ),
    // ... more items
  )
)
```

### VoiceBookCard

Horizontal and vertical book card layouts

```kotlin
VoiceBookCard(
  title = "Book Title",
  author = "Author Name",
  coverUrl = "https://...",
  onClick = { }
)

VoiceBookCardVertical(
  title = "Book Title",
  author = "Author Name",
  coverUrl = "https://...",
  onClick = { }
)
```

## Screens

### Player Screen
Full-featured audio player with:
- Cover art with blur backdrop (319x335px)
- Progress timeline with custom colors
- Playback controls
- Bottom action menu (Bookmark, Chapter, Speed, Download)

### Library Screen
- Header with logo and settings icon
- Search input
- Book cards list with 80x80px covers
- Title (16px Medium), Author (12px Regular)

### Settings Screen
- Header with back navigation
- Profile section with 72x72px avatar
- Menu items with icons
- Logout button (Outline style)

### Search Screen
- Header with logo
- Search input in Explore section
- Category buttons (4 columns)
- Latest Search horizontal scroll (160x196px cards)

## Theme Usage

```kotlin
import voice.core.ui.VoiceTheme

@Composable
fun MyScreen() {
  VoiceTheme {
    // Your composables here
    // Theme automatically switches based on system settings
  }
}
```

## Previews

View component showcases:
- `DesignSystemShowcase.kt` - All components in one screen
- `ThemeShowcase.kt` - Color palette visualization
- Individual screen previews available in `screens/` package

## File Structure

```
core/ui/src/main/kotlin/voice/core/ui/
├── theme/
│   ├── Color.kt         # Color definitions
│   ├── Type.kt          # Typography system
│   └── Theme.kt         # Material3 color schemes
├── components/
│   ├── VoiceButton.kt
│   ├── VoiceInputField.kt
│   ├── VoiceCheckbox.kt
│   ├── VoiceRatingBar.kt
│   ├── VoiceBottomNavBar.kt
│   └── VoiceCard.kt
├── screens/
│   ├── VoicePlayerScreen.kt
│   ├── VoiceLibraryScreen.kt
│   ├── VoiceSettingsScreen.kt
│   └── VoiceSearchScreen.kt
├── showcase/
│   ├── DesignSystemShowcase.kt
│   └── ThemeShowcase.kt
└── VoiceTheme.kt        # Main theme wrapper
```

## Color Resources

All colors are also defined in XML resources at:
- `core/ui/src/main/res/values/colors.xml`

## Accessibility

- WCAG AA compliant contrast ratios
- Minimum touch target size: 48dp
- Semantic colors for states (error, success, etc.)
- Support for system dark mode

## Migration Guide

To use the new design system in existing screens:

1. Import the VoiceTheme wrapper
2. Replace hardcoded colors with `MaterialTheme.colorScheme.*`
3. Replace text styles with `VoiceTypography.*`
4. Use provided components instead of custom implementations
5. Test in both light and dark modes
