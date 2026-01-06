package voice.core.metadata.suggester

import android.net.Uri
import androidx.datastore.core.DataStore
import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.shouldBe
import java.io.File
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.Ignore
import org.junit.Test
import voice.core.documentfile.CachedDocumentFile
import voice.core.metadata.suggester.PathSegmentProvider

@Ignore("Requires Android Uri parsing support in the test runtime")
class FolderStructureAnalyzerTest {

  @Test
  fun authorBookPrefersParentFolderForFiles() = runTest {
    val analyzer = FolderStructureAnalyzer(FakeDataStore(FolderStructurePattern.AUTHOR_BOOK))
    val file = fakeFile("content://test/Audiobooks/Author Name/Book Title/track01.mp3")

    val meta = analyzer.analyze(file)

    meta.bookFolder shouldBe "Book Title"
    meta.authorFolder shouldBe "Author Name"
    meta.seriesFolder.shouldBeNull()
  }

  @Test
  fun authorSeriesBookUsesNestedFolders() = runTest {
    val analyzer = FolderStructureAnalyzer(FakeDataStore(FolderStructurePattern.AUTHOR_SERIES_BOOK))
    val file = fakeFile("content://test/Audiobooks/Author Name/Series Name/Book 01/track01.mp3")

    val meta = analyzer.analyze(file)

    meta.bookFolder shouldBe "Book 01"
    meta.authorFolder shouldBe "Author Name"
    meta.seriesFolder shouldBe "Series Name"
  }

  @Test
  fun authorSeriesBookSplitsCombinedParent() = runTest {
    val analyzer = FolderStructureAnalyzer(FakeDataStore(FolderStructurePattern.AUTHOR_SERIES_BOOK))
    val file = fakeFile("content://test/Audiobooks/Author Name - Series Name/Book One/track01.mp3")

    val meta = analyzer.analyze(file)

    meta.bookFolder shouldBe "Book One"
    meta.authorFolder shouldBe "Author Name"
    meta.seriesFolder shouldBe "Series Name"
  }

  private fun fakeFile(path: String): CachedDocumentFile {
    val localUri = Uri.fromFile(File(path))
    val fileName = path.substringAfterLast('/')
    val segments = path.removePrefix("content://test/")
      .split("/")
      .filter { it.isNotBlank() }
    return TestFile(
      uri = localUri,
      name = fileName.takeIf { it.isNotBlank() },
      isDirectory = false,
      isFile = true,
      pathSegments = segments,
    )
  }

  private class FakeDataStore(
    private val pattern: FolderStructurePattern,
  ) : DataStore<FolderStructurePattern> {
    override val data: Flow<FolderStructurePattern> = flowOf(pattern)
    override suspend fun updateData(transform: suspend (t: FolderStructurePattern) -> FolderStructurePattern): FolderStructurePattern = pattern
  }

  private data class TestFile(
    override val uri: Uri,
    override val name: String?,
    override val isDirectory: Boolean,
    override val isFile: Boolean,
    override val pathSegments: List<String>,
  ) : CachedDocumentFile, PathSegmentProvider {
    override val children: List<CachedDocumentFile> = emptyList()
    override val length: Long = 0
    override val lastModified: Long = 0
  }
}
