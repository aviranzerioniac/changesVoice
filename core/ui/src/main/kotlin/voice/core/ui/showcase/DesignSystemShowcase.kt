package voice.core.ui.showcase

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.Bookmark
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.ui.tooling.preview.Preview
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing
import voice.core.ui.VoiceTheme
import voice.core.ui.components.IconPosition
import voice.core.ui.components.VoiceBookCard
import voice.core.ui.components.VoiceBookCardVertical
import voice.core.ui.components.VoiceBottomNavBar
import voice.core.ui.components.VoiceBottomNavItem
import voice.core.ui.components.VoiceButton
import voice.core.ui.components.VoiceButtonStyle
import voice.core.ui.components.VoiceCheckbox
import voice.core.ui.components.VoiceIconButton
import voice.core.ui.components.VoiceInputField
import voice.core.ui.components.VoiceRatingBar
import voice.core.ui.theme.VoiceTypography

@Composable
@Preview
private fun DesignSystemShowcaseLight() {
  VoiceTheme {
    DesignSystemShowcase()
  }
}

@Composable
fun DesignSystemShowcase(modifier: Modifier = Modifier) {
  Scaffold(
    bottomBar = {
      VoiceBottomNavBar(
        items = listOf(
          VoiceBottomNavItem(
            label = "Home",
            icon = Icons.Default.Add,
            selected = true,
            onClick = {},
          ),
          VoiceBottomNavItem(
            label = "Search",
            icon = Icons.Default.Favorite,
            selected = false,
            onClick = {},
          ),
          VoiceBottomNavItem(
            label = "Library",
            icon = Icons.Default.Bookmark,
            selected = false,
            onClick = {},
          ),
        ),
      )
    },
  ) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(paddingValues)
        .verticalScroll(rememberScrollState())
        .padding(16.dp),
      verticalArrangement = Arrangement.spacedBy(24.dp),
    ) {
      TypographySection()
      HorizontalDivider()
      ButtonSection()
      HorizontalDivider()
      InputSection()
      HorizontalDivider()
      CheckboxSection()
      HorizontalDivider()
      RatingSection()
      HorizontalDivider()
      CardSection()
    }
  }
}

@Composable
private fun TypographySection() {
  Column(
    verticalArrangement = Arrangement.spacedBy(8.dp),
  ) {
    Text(
      text = "Typography",
      style = VoiceTypography.Heading2.semiBold,
      color = MaterialTheme.colorScheme.onBackground,
    )
    Text("Display", style = VoiceTypography.Display.regular)
    Text("Heading 1", style = VoiceTypography.Heading1.regular)
    Text("Heading 2", style = VoiceTypography.Heading2.regular)
    Text("Subheading", style = VoiceTypography.Subheading.regular)
    Text("Body", style = VoiceTypography.Body.regular)
    Text("Body Small", style = VoiceTypography.BodySmall.regular)
    Text("Small", style = VoiceTypography.Small.regular)
    Text("Caption", style = VoiceTypography.Caption.regular)
  }
}

@Composable
private fun ButtonSection() {
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Text(
      text = "Buttons",
      style = VoiceTypography.Heading2.semiBold,
      color = MaterialTheme.colorScheme.onBackground,
    )

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      VoiceButton(
        text = "Flat Button",
        onClick = {},
        style = VoiceButtonStyle.Flat,
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      VoiceButton(
        text = "Outline",
        onClick = {},
        style = VoiceButtonStyle.Outline,
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      VoiceButton(
        text = "Ghost",
        onClick = {},
        style = VoiceButtonStyle.Ghost,
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      VoiceButton(
        text = "With Icon",
        onClick = {},
        style = VoiceButtonStyle.Flat,
        icon = Icons.Default.Add,
        iconPosition = IconPosition.Left,
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      VoiceIconButton(
        icon = Icons.Default.Favorite,
        onClick = {},
        style = VoiceButtonStyle.Flat,
      )
      VoiceIconButton(
        icon = Icons.Default.Favorite,
        onClick = {},
        style = VoiceButtonStyle.Outline,
      )
      VoiceIconButton(
        icon = Icons.Default.Favorite,
        onClick = {},
        style = VoiceButtonStyle.Ghost,
      )
    }

    Row(horizontalArrangement = Arrangement.spacedBy(8.dp)) {
      VoiceButton(
        text = "Disabled",
        onClick = {},
        enabled = false,
      )
    }
  }
}

@Composable
private fun InputSection() {
  var inputValue by remember { mutableStateOf("") }
  var inputWithValue by remember { mutableStateOf("With Value") }

  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Text(
      text = "Input Fields",
      style = VoiceTypography.Heading2.semiBold,
      color = MaterialTheme.colorScheme.onBackground,
    )

    VoiceInputField(
      value = inputValue,
      onValueChange = { inputValue = it },
      placeholder = "Normal State",
      modifier = Modifier.fillMaxWidth(),
    )

    VoiceInputField(
      value = inputWithValue,
      onValueChange = { inputWithValue = it },
      placeholder = "With Value",
      modifier = Modifier.fillMaxWidth(),
    )

    VoiceInputField(
      value = "Disabled",
      onValueChange = {},
      placeholder = "Disabled",
      enabled = false,
      modifier = Modifier.fillMaxWidth(),
    )

    VoiceInputField(
      value = "Read Only",
      onValueChange = {},
      placeholder = "Read Only",
      readOnly = true,
      modifier = Modifier.fillMaxWidth(),
    )
  }
}

@Composable
private fun CheckboxSection() {
  var checked by remember { mutableStateOf(false) }
  var checked2 by remember { mutableStateOf(true) }

  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Text(
      text = "Checkboxes",
      style = VoiceTypography.Heading2.semiBold,
      color = MaterialTheme.colorScheme.onBackground,
    )

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
      VoiceCheckbox(
        checked = checked,
        onCheckedChange = { checked = it },
      )
      Text("Unchecked", modifier = Modifier.padding(start = 8.dp))
    }

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
      VoiceCheckbox(
        checked = checked2,
        onCheckedChange = { checked2 = it },
      )
      Text("Checked", modifier = Modifier.padding(start = 8.dp))
    }

    Row(horizontalArrangement = Arrangement.spacedBy(16.dp)) {
      VoiceCheckbox(
        checked = false,
        onCheckedChange = null,
        enabled = false,
      )
      Text("Disabled", modifier = Modifier.padding(start = 8.dp))
    }
  }
}

@Composable
private fun RatingSection() {
  var rating by remember { mutableIntStateOf(3) }

  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Text(
      text = "Rating",
      style = VoiceTypography.Heading2.semiBold,
      color = MaterialTheme.colorScheme.onBackground,
    )

    VoiceRatingBar(
      rating = rating,
      onRatingChange = { rating = it },
    )

    VoiceRatingBar(
      rating = 4,
      onRatingChange = null,
      enabled = false,
    )
  }
}

@Composable
private fun CardSection() {
  Column(
    verticalArrangement = Arrangement.spacedBy(12.dp),
  ) {
    Text(
      text = "Book Cards",
      style = VoiceTypography.Heading2.semiBold,
      color = MaterialTheme.colorScheme.onBackground,
    )

    VoiceBookCard(
      title = "The Great Gatsby",
      author = "F. Scott Fitzgerald",
      coverUrl = null,
      onClick = {},
    )

    Spacer(modifier = Modifier.height(8.dp))

    Row(horizontalArrangement = Arrangement.spacedBy(12.dp)) {
      VoiceBookCardVertical(
        title = "1984",
        author = "George Orwell",
        coverUrl = null,
        onClick = {},
      )
      VoiceBookCardVertical(
        title = "To Kill a Mockingbird",
        author = "Harper Lee",
        coverUrl = null,
        onClick = {},
      )
    }
  }
}
