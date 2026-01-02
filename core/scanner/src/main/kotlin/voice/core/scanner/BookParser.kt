package voice.core.scanner

import android.net.Uri
import dev.zacsweers.metro.Inject
import voice.core.data.Book
import voice.core.data.BookContent
import voice.core.data.BookId
import voice.core.data.Chapter
import voice.core.data.repo.BookContentRepo
import voice.core.data.repo.getOrPut
import voice.core.data.toUri
import voice.core.documentfile.CachedDocumentFile
import voice.core.documentfile.CachedDocumentFileFactory
import voice.core.logging.api.Logger
import voice.core.metadata.suggester.MetadataSuggester
import java.time.Instant

@Inject
internal class BookParser(
  private val contentRepo: BookContentRepo,
  private val mediaAnalyzer: MediaAnalyzer,
  private val fileFactory: CachedDocumentFileFactory,
  private val metadataSuggester: MetadataSuggester,
) {

  suspend fun parseAndStore(
    chapters: List<Chapter>,
    file: CachedDocumentFile,
  ): BookContent {
    val id = BookId(file.uri)
    return contentRepo.getOrPut(id) {
      val uri = chapters.first().id.toUri()
      val analyzed = mediaAnalyzer.analyze(fileFactory.create(uri))
      
      // Get metadata suggestions based on filename and folder structure
      val suggestions = metadataSuggester.suggestMetadata(
        file = file,
        existingAuthor = analyzed?.artist,
        existingTitle = analyzed?.title,
        existingSeries = analyzed?.series,
      )
      
      // Use the best suggestion or fallback to analyzed/filename
      val bestAuthor = suggestions.authors.firstOrNull()?.value ?: analyzed?.artist
      val bestTitle = suggestions.titles.firstOrNull()?.value 
        ?: analyzed?.album 
        ?: analyzed?.title 
        ?: file.bookName()
      
      Logger.d("Metadata suggestions for ${file.name}: author=$bestAuthor, title=$bestTitle")
      
      BookContent(
        id = id,
        isActive = true,
        addedAt = Instant.now(),
        author = bestAuthor,
        lastPlayedAt = Instant.EPOCH,
        name = bestTitle,
        playbackSpeed = 1F,
        skipSilence = false,
        chapters = chapters.map { it.id },
        positionInChapter = 0L,
        currentChapter = chapters.first().id,
        cover = null,
        gain = 0F,
        genre = analyzed?.genre,
        narrator = suggestions.narrators.firstOrNull()?.value ?: analyzed?.narrator,
        series = suggestions.series.firstOrNull()?.seriesName ?: analyzed?.series,
        part = suggestions.series.firstOrNull()?.part ?: analyzed?.part,
      ).also {
        validateIntegrity(it, chapters)
      }
    }
  }

  private fun CachedDocumentFile.bookName(): String {
    val fileName = name
    return if (fileName == null) {
      uri.toString()
        .removePrefix("/storage/emulated/0/")
        .removePrefix("/storage/emulated/")
        .removePrefix("/storage/")
        .also {
          Logger.w("Could not parse fileName from $this. Fallback to $it")
        }
    } else {
      if (isFile) {
        fileName.substringBeforeLast(".")
      } else {
        fileName
      }
    }
  }
}

internal fun validateIntegrity(
  content: BookContent,
  chapters: List<Chapter>,
) {
  // the init block performs integrity validation
  @Suppress("RETURN_VALUE_NOT_USED")
  Book(content, chapters)
}

internal fun Uri.filePath(): String? {
  return pathSegments.lastOrNull()
    ?.dropWhile { it != ':' }
    ?.removePrefix(":")
}
