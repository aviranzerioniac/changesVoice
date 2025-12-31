package voice.core.ui.screens

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ChevronRight
import androidx.compose.material.icons.filled.Notifications
import androidx.compose.material.icons.filled.Storage
import androidx.compose.material.icons.filled.Subscriptions
import androidx.compose.ui.tooling.preview.Preview
import voice.core.ui.VoiceCompose
import voice.core.ui.VoiceCompose.Spacing
import voice.core.ui.VoiceTheme
import voice.core.ui.components.VoiceButton
import voice.core.ui.components.VoiceButtonStyle
import voice.core.ui.theme.VoiceTypography

@Composable
@Preview
private fun VoiceSettingsScreenPreview() {
  VoiceTheme {
    VoiceSettingsScreen(
      userName = "John Doe",
      userEmail = "john.doe@example.com",
      onBackClick = {},
      onProfileClick = {},
      onNotificationsClick = {},
      onDataStorageClick = {},
      onSubscriptionClick = {},
      onLinkedAccountClick = {},
      onAboutClick = {},
      onLogoutClick = {},
    )
  }
}

@Composable
fun VoiceSettingsScreen(
  userName: String,
  userEmail: String,
  onBackClick: () -> Unit,
  onProfileClick: () -> Unit,
  onNotificationsClick: () -> Unit,
  onDataStorageClick: () -> Unit,
  onSubscriptionClick: () -> Unit,
  onLinkedAccountClick: () -> Unit,
  onAboutClick: () -> Unit,
  onLogoutClick: () -> Unit,
  modifier: Modifier = Modifier,
) {
  Scaffold(modifier = modifier) { paddingValues ->
    Column(
      modifier = Modifier
        .fillMaxSize()
        .background(MaterialTheme.colorScheme.background)
        .padding(paddingValues)
        .verticalScroll(rememberScrollState()),
    ) {
      Row(
        modifier = Modifier
          .fillMaxWidth()
          .padding(16.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        IconButton(onClick = onBackClick) {
          Icon(
            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
            contentDescription = "Back",
            tint = MaterialTheme.colorScheme.onBackground,
          )
        }
        Text(
          text = "Settings",
          style = VoiceTypography.Heading2.semiBold,
          color = MaterialTheme.colorScheme.onBackground,
          modifier = Modifier.padding(start = 8.dp),
        )
      }

      Spacer(modifier = Modifier.height(8.dp))

      Row(
        modifier = Modifier
          .fillMaxWidth()
          .clip(RoundedCornerShape(8.dp))
          .clickable(onClick = onProfileClick)
          .padding(20.dp),
        verticalAlignment = Alignment.CenterVertically,
      ) {
        Box(
          modifier = Modifier
            .size(72.dp)
            .clip(CircleShape)
            .background(MaterialTheme.colorScheme.primaryContainer),
          contentAlignment = Alignment.Center,
        ) {
          Text(
            text = userName.take(1),
            style = VoiceTypography.Heading2.bold,
            color = MaterialTheme.colorScheme.primary,
          )
        }

        Column(
          modifier = Modifier
            .weight(1f)
            .padding(start = 16.dp),
        ) {
          Text(
            text = userName,
            style = VoiceTypography.Body.semiBold,
            color = MaterialTheme.colorScheme.onBackground,
          )
          Spacer(modifier = Modifier.height(4.dp))
          Text(
            text = "View profile",
            style = VoiceTypography.BodySmall.regular,
            color = MaterialTheme.colorScheme.primary,
          )
        }

        Icon(
          imageVector = Icons.Default.ChevronRight,
          contentDescription = null,
          tint = MaterialTheme.colorScheme.onSurfaceVariant,
        )
      }

      Spacer(modifier = Modifier.height(16.dp))
      HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
      Spacer(modifier = Modifier.height(16.dp))

      Column(
        modifier = Modifier.padding(horizontal = 20.dp),
        verticalArrangement = Arrangement.spacedBy(4.dp),
      ) {
        SettingsMenuItem(
          icon = Icons.Default.Notifications,
          title = "Notifications",
          onClick = onNotificationsClick,
        )
        SettingsMenuItem(
          icon = Icons.Default.Storage,
          title = "Data & Storage",
          onClick = onDataStorageClick,
        )
        SettingsMenuItem(
          icon = Icons.Default.Subscriptions,
          title = "Subscription",
          onClick = onSubscriptionClick,
        )
        SettingsMenuItem(
          icon = Icons.Default.Notifications,
          title = "Linked Account",
          onClick = onLinkedAccountClick,
        )
        SettingsMenuItem(
          icon = Icons.Default.Notifications,
          title = "About",
          onClick = onAboutClick,
        )
      }

      Spacer(modifier = Modifier.height(32.dp))

      VoiceButton(
        text = "Logout",
        onClick = onLogoutClick,
        style = VoiceButtonStyle.Outline,
        modifier = Modifier
          .fillMaxWidth()
          .padding(horizontal = 20.dp),
      )

      Spacer(modifier = Modifier.height(32.dp))
    }
  }
}

@Composable
private fun SettingsMenuItem(
  icon: ImageVector,
  title: String,
  onClick: () -> Unit,
) {
  Row(
    modifier = Modifier
      .fillMaxWidth()
      .clip(RoundedCornerShape(8.dp))
      .clickable(onClick = onClick)
      .padding(vertical = 16.dp, horizontal = 12.dp),
    verticalAlignment = Alignment.CenterVertically,
  ) {
    Icon(
      imageVector = icon,
      contentDescription = null,
      tint = MaterialTheme.colorScheme.onSurfaceVariant,
      modifier = Modifier.size(24.dp),
    )
    Text(
      text = title,
      style = VoiceTypography.Body.regular,
      color = MaterialTheme.colorScheme.onBackground,
      modifier = Modifier
        .weight(1f)
        .padding(start = 16.dp),
    )
    Icon(
      imageVector = Icons.Default.ChevronRight,
      contentDescription = null,
      tint = MaterialTheme.colorScheme.onSurfaceVariant,
    )
  }
}
