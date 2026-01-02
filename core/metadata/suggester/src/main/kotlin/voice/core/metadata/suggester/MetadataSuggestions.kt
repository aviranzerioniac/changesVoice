package voice.core.metadata.suggester

/**
 * Represents suggested metadata values with confidence scores
 */
data class MetadataSuggestions(
  val authors: List<SuggestedValue>,
  val titles: List<SuggestedValue>,
  val narrators: List<SuggestedValue>,
  val series: List<SeriesSuggestion>,
  val genres: List<SuggestedValue>,
)

/**
 * A single suggested value with its source and confidence
 */
data class SuggestedValue(
  val value: String,
  val confidence: Float, // 0.0 - 1.0
  val source: MetadataSource,
)

/**
 * Series suggestion with optional part number
 */
data class SeriesSuggestion(
  val seriesName: String,
  val part: String?,
  val confidence: Float,
  val source: MetadataSource,
)

/**
 * Source of metadata information
 */
enum class MetadataSource {
  /** Extracted from filename */
  FILENAME,

  /** Extracted from folder structure */
  FOLDER_STRUCTURE,

  /** Embedded in audio file tags */
  EMBEDDED_TAGS,

  /** Previously entered by user */
  USER_INPUT,
}
