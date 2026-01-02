package voice.core.metadata.suggester

import androidx.datastore.core.DataStore
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.first
import voice.core.documentfile.CachedDocumentFile

/**
 * Analyzes folder structure to extract metadata based on configured pattern
 */
@Inject
class FolderStructureAnalyzer(
  @FolderStructurePatternStore
  private val patternStore: DataStore<FolderStructurePattern>,
) {

  /**
   * Analyzes folder hierarchy to extract metadata based on the configured pattern
   */
  suspend fun analyze(file: CachedDocumentFile): FolderMetadata {
    val pattern = patternStore.data.first()
    val folderName = file.name ?: file.uri.lastPathSegment ?: ""
    val parentName = extractParentFolderName(file)
    val grandparentName = extractGrandparentFolderName(file)

    return when (pattern) {
      FolderStructurePattern.BOOK_ONLY -> {
        FolderMetadata(
          bookFolder = cleanFolderName(folderName),
          authorFolder = null,
          seriesFolder = null,
        )
      }
      FolderStructurePattern.AUTHOR_BOOK -> {
        FolderMetadata(
          bookFolder = cleanFolderName(folderName),
          authorFolder = parentName?.let { cleanFolderName(it) },
          seriesFolder = null,
        )
      }
      FolderStructurePattern.SERIES_BOOK -> {
        FolderMetadata(
          bookFolder = cleanFolderName(folderName),
          authorFolder = null,
          seriesFolder = parentName?.let { cleanFolderName(it) },
        )
      }
      FolderStructurePattern.AUTHOR_SERIES_BOOK -> {
        FolderMetadata(
          bookFolder = cleanFolderName(folderName),
          authorFolder = grandparentName?.let { cleanFolderName(it) },
          seriesFolder = parentName?.let { cleanFolderName(it) },
        )
      }
    }
  }

  private fun extractParentFolderName(file: CachedDocumentFile): String? {
    val path = file.uri.path ?: return null
    val segments = path.split("/").filter { it.isNotBlank() }
    return segments.getOrNull(segments.size - 2)
  }

  private fun extractGrandparentFolderName(file: CachedDocumentFile): String? {
    val path = file.uri.path ?: return null
    val segments = path.split("/").filter { it.isNotBlank() }
    return segments.getOrNull(segments.size - 3)
  }

  private fun cleanFolderName(name: String): String {
    return name
      .replace("_", " ")
      .trim()
  }
}

data class FolderMetadata(
  val bookFolder: String,
  val authorFolder: String?,
  val seriesFolder: String?,
)
