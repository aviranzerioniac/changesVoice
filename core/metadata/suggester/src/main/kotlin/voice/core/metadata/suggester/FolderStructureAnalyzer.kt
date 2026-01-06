package voice.core.metadata.suggester

import android.net.Uri
import androidx.datastore.core.DataStore
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.flow.first
import voice.core.documentfile.CachedDocumentFile
import voice.core.documentfile.nameWithoutExtension

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
    val pathSegments = extractPathSegments(file)
    val folderSegments = if (file.isFile && pathSegments.isNotEmpty()) {
      pathSegments.dropLast(1)
    } else {
      pathSegments
    }

    val leafName = folderSegments.lastOrNull()
    val parentName = folderSegments.getOrNull(folderSegments.size - 2)
    val grandparentName = folderSegments.getOrNull(folderSegments.size - 3)

    val cleanedParent = parentName?.let(::cleanFolderName)
    val cleanedGrandparent = grandparentName?.let(::cleanFolderName)

    val bookFolder = when {
      file.isDirectory -> cleanFolderName(leafName ?: file.name ?: "")
      cleanedParent != null -> cleanedParent
      else -> cleanFolderName(file.nameWithoutExtension())
    }

    return when (pattern) {
      FolderStructurePattern.BOOK_ONLY -> {
        FolderMetadata(
          bookFolder = bookFolder,
          authorFolder = null,
          seriesFolder = null,
        )
      }
      FolderStructurePattern.AUTHOR_BOOK -> {
        FolderMetadata(
          bookFolder = bookFolder,
          authorFolder = cleanedParent,
          seriesFolder = null,
        )
      }
      FolderStructurePattern.SERIES_BOOK -> {
        FolderMetadata(
          bookFolder = bookFolder,
          authorFolder = null,
          seriesFolder = cleanedParent,
        )
      }
      FolderStructurePattern.AUTHOR_SERIES_BOOK -> {
        val combinedFromParent = cleanedParent?.let(::splitAuthorSeries)
        val combinedFromGrandparent = cleanedGrandparent?.let(::splitAuthorSeries)
        FolderMetadata(
          bookFolder = bookFolder,
          authorFolder = cleanedGrandparent
            ?: combinedFromParent?.first
            ?: combinedFromGrandparent?.first,
          seriesFolder = combinedFromParent?.second
            ?: cleanedParent
            ?: combinedFromGrandparent?.second,
        )
      }
    }
  }

  private fun extractPathSegments(file: CachedDocumentFile): List<String> {
    if (file is PathSegmentProvider) {
      return file.pathSegments
    }

    val decodedLast = file.uri.lastPathSegment
      ?.let { Uri.decode(it) }
      ?.split("/", ":")
      ?.filter { it.isNotBlank() }
      .orEmpty()

    if (decodedLast.size >= 2) {
      return decodedLast
    }

    val decodedPath = file.uri.path
      ?.let { Uri.decode(it) }
      ?.split("/", ":")
      ?.filter { it.isNotBlank() }
      .orEmpty()

    return if (decodedPath.isNotEmpty()) decodedPath else listOfNotNull(file.name)
  }

  private fun cleanFolderName(name: String): String {
    return name
      .replace("_", " ")
      .replace(Regex("\\s+"), " ")
      .trim()
  }

  private fun splitAuthorSeries(name: String): Pair<String, String>? {
    val match = """(.+?)\s*[-–]\s*(.+)""".toRegex().matchEntire(name) ?: return null
    val author = match.groupValues.getOrNull(1)?.trim().orEmpty()
    val series = match.groupValues.getOrNull(2)?.trim().orEmpty()
    if (author.isBlank() || series.isBlank()) return null
    return author to series
  }
}

internal interface PathSegmentProvider {
  val pathSegments: List<String>
}

data class FolderMetadata(
  val bookFolder: String,
  val authorFolder: String?,
  val seriesFolder: String?,
)
