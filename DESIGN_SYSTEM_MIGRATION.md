# Voice Design System Migration Guide

This guide helps you migrate existing screens to use the new Voice Design System with full light/dark mode support.

## Overview

The new design system provides:
- **33+ colors** for light and dark modes
- **40 typography styles** (8 sizes × 5 weights)
- **Reusable UI components** (buttons, inputs, checkboxes, rating, bottom nav, cards)
- **Complete screen templates** (player, library, settings, search)
- **Material3 theming** with custom brand colors

## Quick Start

### 1. Wrap Your Composables with VoiceTheme

```kotlin
import voice.core.ui.VoiceTheme

@Composable
fun MyScreen() {
  VoiceTheme {
    // Your UI here - theme switches automatically
  }
}
```

### 2. Replace Hardcoded Colors

**Before:**
```kotlin
Text(
  text = "Hello",
  color = Color(0xFF4838D1)
)
```

**After:**
```kotlin
Text(
  text = "Hello",
  color = MaterialTheme.colorScheme.primary
)
```

### 3. Use Typography System

**Before:**
```kotlin
Text(
  text = "Title",
  fontSize = 24.sp,
  fontWeight = FontWeight.Bold
)
```

**After:**
```kotlin
import voice.core.ui.theme.VoiceTypography

Text(
  text = "Title",
  style = VoiceTypography.Heading2.bold
)
```

### 4. Use Design System Components

**Before:**
```kotlin
Button(onClick = { }) {
  Text("Click Me")
}
```

**After:**
```kotlin
import voice.core.ui.components.VoiceButton
import voice.core.ui.components.VoiceButtonStyle

VoiceButton(
  text = "Click Me",
  onClick = { },
  style = VoiceButtonStyle.Flat
)
```

## Component Migration

### Buttons

```kotlin
// Flat button (primary action)
VoiceButton(
  text = "Save",
  onClick = { },
  style = VoiceButtonStyle.Flat
)

// Outline button (secondary action)
VoiceButton(
  text = "Cancel",
  onClick = { },
  style = VoiceButtonStyle.Outline
)

// Ghost button (tertiary action)
VoiceButton(
  text = "Skip",
  onClick = { },
  style = VoiceButtonStyle.Ghost
)

// With icon
VoiceButton(
  text = "Add",
  onClick = { },
  icon = Icons.Default.Add,
  iconPosition = IconPosition.Left
)

// Icon-only
VoiceIconButton(
  icon = Icons.Default.Star,
  onClick = { },
  style = VoiceButtonStyle.Flat
)
```

### Input Fields

```kotlin
var text by remember { mutableStateOf("") }

VoiceInputField(
  value = text,
  onValueChange = { text = it },
  placeholder = "Enter text...",
  modifier = Modifier.fillMaxWidth()
)

// Disabled state
VoiceInputField(
  value = text,
  onValueChange = { },
  enabled = false
)

// Read-only state
VoiceInputField(
  value = text,
  onValueChange = { },
  readOnly = true
)
```

### Checkboxes

```kotlin
var checked by remember { mutableStateOf(false) }

VoiceCheckbox(
  checked = checked,
  onCheckedChange = { checked = it }
)
```

### Rating

```kotlin
var rating by remember { mutableStateOf(3) }

VoiceRatingBar(
  rating = rating,
  onRatingChange = { rating = it },
  maxRating = 5
)
```

### Bottom Navigation

```kotlin
VoiceBottomNavBar(
  items = listOf(
    VoiceBottomNavItem(
      label = "Home",
      icon = Icons.Default.Home,
      selected = currentTab == 0,
      onClick = { currentTab = 0 }
    ),
    VoiceBottomNavItem(
      label = "Search",
      icon = Icons.Default.Search,
      selected = currentTab == 1,
      onClick = { currentTab = 1 }
    ),
    VoiceBottomNavItem(
      label = "Library",
      icon = Icons.Outlined.Description,
      selected = currentTab == 2,
      onClick = { currentTab = 2 }
    )
  )
)
```

### Book Cards

```kotlin
// Horizontal card (list view)
VoiceBookCard(
  title = "Book Title",
  author = "Author Name",
  coverUrl = coverUrl,
  onClick = { }
)

// Vertical card (grid view)
VoiceBookCardVertical(
  title = "Book Title",
  author = "Author Name",
  coverUrl = coverUrl,
  onClick = { }
)
```

## Color Reference

### Light Mode

| Purpose | Color | Value |
|---------|-------|-------|
| Primary | `MaterialTheme.colorScheme.primary` | #4838D1 |
| Secondary (Accent) | `MaterialTheme.colorScheme.secondary` | #F77A55 |
| Background | `MaterialTheme.colorScheme.background` | #FFFFFF |
| Surface | `MaterialTheme.colorScheme.surface` | #FFFFFF |
| Text Primary | `MaterialTheme.colorScheme.onBackground` | #0F0F29 |
| Text Secondary | `MaterialTheme.colorScheme.onSurfaceVariant` | #6A6A8B |

### Dark Mode

| Purpose | Color | Value |
|---------|-------|-------|
| Primary | `MaterialTheme.colorScheme.primary` | #7B6AF0 |
| Secondary (Accent) | `MaterialTheme.colorScheme.secondary` | #FF9975 |
| Background | `MaterialTheme.colorScheme.background` | #0F0F1D |
| Surface | `MaterialTheme.colorScheme.surface` | #0F0F1D |
| Surface Variant | `MaterialTheme.colorScheme.surfaceVariant` | #1A1A2E |
| Text Primary | `MaterialTheme.colorScheme.onBackground` | #FFFFFF |
| Text Secondary | `MaterialTheme.colorScheme.onSurfaceVariant` | #B8B8C7 |

### Advanced Colors

For more granular control, use the VoiceColors object:

```kotlin
import voice.core.ui.theme.VoiceColors

// Light mode
val lightPrimary = VoiceColors.Light.PrimaryBlue.shade50
val lightAccent = VoiceColors.Light.AccentOrange.shade50

// Dark mode
val darkPrimary = VoiceColors.Dark.PrimaryBlueBright.shade60
val darkAccent = VoiceColors.Dark.AccentOrangeBright.shade50
```

## Typography Reference

All typography uses the Poppins font family (with system fallback).

```kotlin
import voice.core.ui.theme.VoiceTypography

// Display (48px)
VoiceTypography.Display.bold

// Heading 1 (32px)
VoiceTypography.Heading1.semiBold

// Heading 2 (24px)
VoiceTypography.Heading2.medium

// Subheading (20px)
VoiceTypography.Subheading.regular

// Body (16px)
VoiceTypography.Body.medium

// Body Small (14px)
VoiceTypography.BodySmall.regular

// Small (12px)
VoiceTypography.Small.regular

// Caption (10px)
VoiceTypography.Caption.light
```

## Testing Both Themes

### Preview in Android Studio

Add `@Preview` annotations with theme wrappers:

```kotlin
@Preview(name = "Light Mode")
@Composable
fun MyScreenPreviewLight() {
  VoiceTheme {
    MyScreen()
  }
}
```

### Runtime Theme Switching

The theme automatically follows system dark mode settings. To manually test:
1. Go to device Settings → Display → Dark theme
2. Toggle dark mode on/off
3. App theme updates automatically

## Screen Templates

The design system includes complete screen templates:

- **VoicePlayerScreen** - Full-featured audio player
- **VoiceLibraryScreen** - Book library with search
- **VoiceSettingsScreen** - Settings with profile and menu
- **VoiceSearchScreen** - Explore and search interface

These are available in `voice.core.ui.screens` package for reference or direct use.

## Showcases

View the design system in action:

- **DesignSystemShowcase** - All components in one screen
- **ThemeShowcase** - Color palette visualization
- **FullAppShowcase** - Complete app flow example

## Common Patterns

### Card with Custom Background

```kotlin
Surface(
  modifier = Modifier.fillMaxWidth(),
  color = MaterialTheme.colorScheme.surfaceVariant,
  shape = RoundedCornerShape(8.dp)
) {
  // Content
}
```

### Divider

```kotlin
HorizontalDivider(
  color = MaterialTheme.colorScheme.outlineVariant
)
```

### Icon with Proper Color

```kotlin
Icon(
  imageVector = Icons.Default.Star,
  contentDescription = "Favorite",
  tint = MaterialTheme.colorScheme.onSurfaceVariant
)
```

### Disabled State

```kotlin
val alpha = if (enabled) 1f else 0.38f

Text(
  text = "Disabled",
  color = MaterialTheme.colorScheme.onSurface.copy(alpha = alpha)
)
```

## Best Practices

1. **Always use MaterialTheme.colorScheme** for colors
2. **Use VoiceTypography** for all text styles
3. **Prefer design system components** over custom implementations
4. **Test in both light and dark modes**
5. **Use semantic color names** (primary, secondary, error) not specific colors
6. **Follow Material3 spacing** (4dp increments)
7. **Keep minimum touch targets** at 48dp
8. **Use provided shapes** from MaterialTheme

## Need Help?

- Check `core/ui/DESIGN_SYSTEM.md` for detailed documentation
- View showcase screens in `voice.core.ui.showcase`
- Reference screen templates in `voice.core.ui.screens`
- Check component source code in `voice.core.ui.components`
