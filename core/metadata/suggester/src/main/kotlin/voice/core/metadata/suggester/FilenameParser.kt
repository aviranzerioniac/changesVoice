package voice.core.metadata.suggester

import dev.zacsweers.metro.Inject

/**
 * Parses audiobook filenames to extract metadata
 */
@Inject
class FilenameParser {

  /**
   * Common audiobook filename patterns:
   * - "Author Name - Book Title.mp3"
   * - "[Author Name] Book Title.m4b"
   * - "Author Name - Book Title - Part 01.mp3"
   * - "Series Name 01 - Book Title.mp3"
   * - "Book Title (Narrator Name).m4b"
   */
  fun parse(filename: String): ParsedFilename {
    val cleanName = filename
      .substringBeforeLast(".")
      .replace("_", " ")
      .replace(Regex("\\s+"), " ")
      .trim()

    // Pattern 0: Author - Series - Title (most specific)
    val tripleDashPattern = """(.+?)\s*[-–]\s*(.+?)\s*[-–]\s*(.+)""".toRegex()
    tripleDashPattern.find(cleanName)?.let {
      val authorPart = it.groupValues.getOrNull(1).cleanOrNull()
      val seriesPart = it.groupValues.getOrNull(2).cleanOrNull()
      val titlePart = it.groupValues.getOrNull(3).cleanOrNull()

      return ParsedFilename(
        author = authorPart,
        title = titlePart,
        narrator = null,
        series = seriesPart?.let { seriesName ->
          extractSeries(seriesName)
            ?: extractSeries(titlePart)?.let { extracted ->
              SeriesInfo(name = seriesName, part = extracted.part)
            }
            ?: SeriesInfo(name = seriesName, part = null)
        } ?: extractSeries(titlePart),
      )
    }

    // Pattern 1: [Author] Title (Narrator)
    val bracketPattern = """\[([^\]]+)\]\s*([^(]+)(?:\(([^)]+)\))?""".toRegex()
    bracketPattern.find(cleanName)?.let {
      val authorPart = it.groupValues.getOrNull(1).cleanOrNull()
      val titlePart = it.groupValues.getOrNull(2).cleanOrNull()
      val narratorPart = it.groupValues.getOrNull(3).cleanOrNull()

      return ParsedFilename(
        author = authorPart,
        title = titlePart,
        narrator = narratorPart,
        series = extractSeries(titlePart),
      )
    }

    // Pattern 2: Author - Title (Narrator)
    val dashPattern = """(.+?)\s*[-–]\s*([^(]+?)(?:\(([^)]+)\))?$""".toRegex()
    dashPattern.find(cleanName)?.let {
      val authorPart = it.groupValues.getOrNull(1).cleanOrNull()
      val titlePart = it.groupValues.getOrNull(2).cleanOrNull()
      val narratorPart = it.groupValues.getOrNull(3).cleanOrNull()

      return ParsedFilename(
        author = authorPart,
        title = titlePart,
        narrator = narratorPart,
        series = extractSeries(titlePart),
      )
    }

    // Pattern 3: Title (Narrator)
    val narratorPattern = """([^(]+)\(([^)]+)\)""".toRegex()
    narratorPattern.find(cleanName)?.let {
      val titlePart = it.groupValues.getOrNull(1).cleanOrNull()
      val narratorPart = it.groupValues.getOrNull(2).cleanOrNull()

      return ParsedFilename(
        author = null,
        title = titlePart,
        narrator = narratorPart,
        series = extractSeries(titlePart),
      )
    }

    // Fallback: use entire filename as title
    return ParsedFilename(
      author = null,
      title = cleanName,
      narrator = null,
      series = extractSeries(cleanName),
    )
  }

  private fun extractSeries(text: String?): SeriesInfo? {
    if (text == null) return null

    // Pattern: "Series Name 01" or "Series Name #1" or "Series Name Book 1"
    val seriesPatterns = listOf(
      """(.+?)\s+Part\s+(\d+)$""".toRegex(RegexOption.IGNORE_CASE),
      """(.+?)\s+Vol(?:ume)?\s*(\d+)$""".toRegex(RegexOption.IGNORE_CASE),
      """(.+?)\s+Book\s+(\d+)$""".toRegex(RegexOption.IGNORE_CASE),
      """(.+?)\s+#(\d+)$""".toRegex(),
      """(.+?)\s+(\d+)$""".toRegex(),
    )

    for (pattern in seriesPatterns) {
      pattern.find(text)?.let {
        return SeriesInfo(
          name = it.groupValues.getOrNull(1)?.trim() ?: return@let null,
          part = it.groupValues.getOrNull(2)?.trim(),
        )
      }
    }

    return null
  }

  private fun String?.cleanOrNull(): String? = this?.trim()?.takeUnless { it.isEmpty() }
}

data class ParsedFilename(
  val author: String?,
  val title: String?,
  val narrator: String?,
  val series: SeriesInfo?,
)

data class SeriesInfo(
  val name: String,
  val part: String?,
)
