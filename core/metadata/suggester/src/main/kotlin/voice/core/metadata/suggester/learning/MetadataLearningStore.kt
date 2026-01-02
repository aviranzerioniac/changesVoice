package voice.core.metadata.suggester.learning

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import voice.core.metadata.suggester.MetadataSource

/**
 * Stores and retrieves user metadata corrections to improve future suggestions
 */
@SingleIn(AppScope::class)
@Inject
class MetadataLearningStore(
  private val context: Context,
) {

  private val Context.learningDataStore: DataStore<Preferences> by preferencesDataStore(name = "metadata_learning")

  /**
   * Records a user's metadata choice for a file or folder pattern
   */
  suspend fun recordCorrection(
    pattern: String,
    field: MetadataField,
    userValue: String,
    suggestedValue: String,
    source: MetadataSource,
  ) {
    val key = stringPreferencesKey("${pattern}_${field.name}_${source.name}")
    context.learningDataStore.edit { preferences ->
      preferences[key] = userValue
    }
  }

  /**
   * Gets the confidence boost for a suggestion based on past user corrections
   * Returns 0.0 if no learning data, or a boost between 0.0 and 0.2
   */
  suspend fun getConfidenceBoost(
    pattern: String,
    field: MetadataField,
    value: String,
    source: MetadataSource,
  ): Float {
    val key = stringPreferencesKey("${pattern}_${field.name}_${source.name}")
    val storedValue = context.learningDataStore.data
      .map { it[key] }
      .first()

    return when {
      storedValue == null -> 0.0f
      storedValue == value -> 0.2f // User previously accepted this exact value
      storedValue.contains(value, ignoreCase = true) -> 0.1f // Partial match
      else -> -0.1f // User previously rejected this value
    }
  }

  /**
   * Clears all learning data
   */
  suspend fun clear() {
    context.learningDataStore.edit { it.clear() }
  }
}

enum class MetadataField {
  AUTHOR,
  TITLE,
  NARRATOR,
  SERIES,
  GENRE,
}
