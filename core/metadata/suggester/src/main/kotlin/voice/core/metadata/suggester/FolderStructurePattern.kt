package voice.core.metadata.suggester

/**
 * Defines how audiobook folders are organized
 */
enum class FolderStructurePattern {
  /**
   * Books only, no author or series folders
   * Example: /Audiobooks/BookName/
   */
  BOOK_ONLY,

  /**
   * Author folders containing book folders
   * Example: /Audiobooks/AuthorName/BookName/
   */
  AUTHOR_BOOK,

  /**
   * Series folders containing book folders
   * Example: /Audiobooks/SeriesName/BookName/
   */
  SERIES_BOOK,

  /**
   * Author folders containing series folders containing book folders
   * Example: /Audiobooks/AuthorName/SeriesName/BookName/
   */
  AUTHOR_SERIES_BOOK,
}
