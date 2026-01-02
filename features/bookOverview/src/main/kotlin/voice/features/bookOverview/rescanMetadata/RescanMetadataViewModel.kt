package voice.features.bookOverview.rescanMetadata

import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import voice.core.data.BookId
import voice.core.data.repo.BookRepository
import voice.core.documentfile.CachedDocumentFileFactory
import voice.core.metadata.suggester.MetadataSuggester
import voice.core.scanner.MediaAnalyzer
import voice.features.bookOverview.bottomSheet.BottomSheetItem
import voice.features.bookOverview.bottomSheet.BottomSheetItemViewModel
import voice.features.bookOverview.di.BookOverviewScope
import voice.core.data.toUri

@BookOverviewScope
@ContributesIntoSet(BookOverviewScope::class)
@Inject
class RescanMetadataViewModel(
  private val repo: BookRepository,
  private val mediaAnalyzer: MediaAnalyzer,
  private val metadataSuggester: MetadataSuggester,
  private val fileFactory: CachedDocumentFileFactory,
) : BottomSheetItemViewModel {

  private val scope = MainScope()

  override suspend fun items(bookId: BookId): List<BottomSheetItem> {
    return listOf(BottomSheetItem.RescanMetadata)
  }

  override suspend fun onItemClick(
    bookId: BookId,
    item: BottomSheetItem,
  ) {
    if (item != BottomSheetItem.RescanMetadata) return
    scope.launch {
      rescanMetadata(bookId)
    }
  }

  private suspend fun rescanMetadata(bookId: BookId) {
    val book = repo.get(bookId) ?: return
    
    // Analyze the first chapter to get updated metadata
    val firstChapterId = book.chapters.firstOrNull()?.id ?: return
    val file = fileFactory.create(firstChapterId.toUri())
    val analyzed = mediaAnalyzer.analyze(file)
    
    // Get folder-based suggestions
    val bookFile = fileFactory.create(bookId.toUri())
    val suggestions = metadataSuggester.suggestMetadata(
      file = bookFile,
      existingAuthor = analyzed?.artist,
      existingTitle = analyzed?.title,
      existingSeries = analyzed?.series,
    )
    
    // Use the best suggestion or analyzed data
    val bestAuthor = suggestions.authors.firstOrNull()?.value ?: analyzed?.artist
    val bestTitle = suggestions.titles.firstOrNull()?.value 
      ?: analyzed?.album 
      ?: analyzed?.title
    
    // Update book content with refreshed metadata
    repo.updateBook(bookId) { content ->
      content.copy(
        author = bestAuthor,
        name = bestTitle ?: content.name,
        narrator = suggestions.narrators.firstOrNull()?.value ?: analyzed?.narrator ?: content.narrator,
        series = suggestions.series.firstOrNull()?.seriesName ?: analyzed?.series ?: content.series,
        part = suggestions.series.firstOrNull()?.part ?: analyzed?.part ?: content.part,
        genre = analyzed?.genre ?: content.genre,
      )
    }
  }
}
