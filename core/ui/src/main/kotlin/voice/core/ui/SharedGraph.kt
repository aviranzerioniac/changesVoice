package voice.core.ui

import androidx.datastore.core.DataStore
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import voice.core.data.ThemeOption
import voice.core.data.store.DarkThemeStore
import voice.core.data.store.ThemeStore

@ContributesTo(AppScope::class)
interface SharedGraph {

  @get:DarkThemeStore
  val useDarkThemeStore: DataStore<Boolean>

  @get:ThemeStore
  val themeStore: DataStore<ThemeOption>
}
