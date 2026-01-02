package voice.core.metadata.suggester

import voice.core.documentfile.CachedDocumentFile

/**
 * Interface for suggesting metadata based on file structure and names
 */
interface MetadataSuggester {

  /**
   * Suggests metadata for an audiobook based on file structure, names, and optionally existing metadata
   *
   * @param file The audiobook file or folder
   * @param existingAuthor Existing author from embedded tags, if any
   * @param existingTitle Existing title from embedded tags, if any
   * @param existingSeries Existing series from embedded tags, if any
   * @return Suggested metadata values
   */
  suspend fun suggestMetadata(
    file: CachedDocumentFile,
    existingAuthor: String? = null,
    existingTitle: String? = null,
    existingSeries: String? = null,
  ): MetadataSuggestions
}
