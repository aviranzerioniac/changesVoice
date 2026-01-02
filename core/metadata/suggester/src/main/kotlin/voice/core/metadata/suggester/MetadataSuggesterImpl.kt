package voice.core.metadata.suggester

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesBinding
import dev.zacsweers.metro.Inject
import dev.zacsweers.metro.SingleIn
import voice.core.documentfile.CachedDocumentFile
import voice.core.metadata.suggester.learning.MetadataLearningStore

@SingleIn(AppScope::class)
@ContributesBinding(AppScope::class)
@Inject
class MetadataSuggesterImpl(
  private val filenameParser: FilenameParser,
  private val folderAnalyzer: FolderStructureAnalyzer,
  private val learningStore: MetadataLearningStore,
) : MetadataSuggester {

  override suspend fun suggestMetadata(
    file: CachedDocumentFile,
    existingAuthor: String?,
    existingTitle: String?,
    existingSeries: String?,
  ): MetadataSuggestions {
    val suggestions = mutableListOf<Suggestion>()

    // Parse filename
    val parsed = filenameParser.parse(file.name ?: "")
    parsed.author?.let {
      suggestions.add(Suggestion.Author(it, 0.7f, MetadataSource.FILENAME))
    }
    parsed.title?.let {
      suggestions.add(Suggestion.Title(it, 0.7f, MetadataSource.FILENAME))
    }
    parsed.narrator?.let {
      suggestions.add(Suggestion.Narrator(it, 0.8f, MetadataSource.FILENAME))
    }
    parsed.series?.let {
      suggestions.add(Suggestion.Series(it.name, it.part, 0.7f, MetadataSource.FILENAME))
    }

    // Analyze folder structure
    val folderMeta = folderAnalyzer.analyze(file)
    folderMeta.authorFolder?.let {
      suggestions.add(Suggestion.Author(it, 0.8f, MetadataSource.FOLDER_STRUCTURE))
    }
    folderMeta.bookFolder.takeIf { it.isNotBlank() }?.let {
      suggestions.add(Suggestion.Title(it, 0.6f, MetadataSource.FOLDER_STRUCTURE))
    }
    folderMeta.seriesFolder?.let {
      suggestions.add(Suggestion.Series(it, null, 0.6f, MetadataSource.FOLDER_STRUCTURE))
    }

    // Add existing embedded metadata with highest confidence
    existingAuthor?.let {
      suggestions.add(Suggestion.Author(it, 1.0f, MetadataSource.EMBEDDED_TAGS))
    }
    existingTitle?.let {
      suggestions.add(Suggestion.Title(it, 1.0f, MetadataSource.EMBEDDED_TAGS))
    }
    existingSeries?.let {
      suggestions.add(Suggestion.Series(it, null, 1.0f, MetadataSource.EMBEDDED_TAGS))
    }

    return mergeSuggestions(suggestions)
  }

  private fun mergeSuggestions(suggestions: List<Suggestion>): MetadataSuggestions {
    val authors = suggestions
      .filterIsInstance<Suggestion.Author>()
      .groupBy { it.value.lowercase().trim() }
      .map { (_, group) -> group.maxBy { it.confidence } }
      .sortedByDescending { it.confidence }
      .map { SuggestedValue(it.value, it.confidence, it.source) }

    val titles = suggestions
      .filterIsInstance<Suggestion.Title>()
      .groupBy { it.value.lowercase().trim() }
      .map { (_, group) -> group.maxBy { it.confidence } }
      .sortedByDescending { it.confidence }
      .map { SuggestedValue(it.value, it.confidence, it.source) }

    val narrators = suggestions
      .filterIsInstance<Suggestion.Narrator>()
      .groupBy { it.value.lowercase().trim() }
      .map { (_, group) -> group.maxBy { it.confidence } }
      .sortedByDescending { it.confidence }
      .map { SuggestedValue(it.value, it.confidence, it.source) }

    val series = suggestions
      .filterIsInstance<Suggestion.Series>()
      .groupBy { it.name.lowercase().trim() }
      .map { (_, group) -> group.maxBy { it.confidence } }
      .sortedByDescending { it.confidence }
      .map { SeriesSuggestion(it.name, it.part, it.confidence, it.source) }

    return MetadataSuggestions(
      authors = authors,
      titles = titles,
      narrators = narrators,
      series = series,
      genres = emptyList(), // To be implemented later
    )
  }

  private sealed interface Suggestion {
    val confidence: Float
    val source: MetadataSource

    data class Author(
      val value: String,
      override val confidence: Float,
      override val source: MetadataSource,
    ) : Suggestion

    data class Title(
      val value: String,
      override val confidence: Float,
      override val source: MetadataSource,
    ) : Suggestion

    data class Narrator(
      val value: String,
      override val confidence: Float,
      override val source: MetadataSource,
    ) : Suggestion

    data class Series(
      val name: String,
      val part: String?,
      override val confidence: Float,
      override val source: MetadataSource,
    ) : Suggestion
  }
}
