package voice.features.bookOverview.editMetadata

import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import dev.zacsweers.metro.ContributesIntoSet
import dev.zacsweers.metro.Inject
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import voice.core.data.BookId
import voice.core.data.repo.BookRepository
import voice.features.bookOverview.bottomSheet.BottomSheetItem
import voice.features.bookOverview.bottomSheet.BottomSheetItemViewModel
import voice.features.bookOverview.di.BookOverviewScope

@BookOverviewScope
@ContributesIntoSet(BookOverviewScope::class)
@Inject
class EditBookMetadataViewModel(private val repo: BookRepository) : BottomSheetItemViewModel {

  private val scope = MainScope()

  private val _state = mutableStateOf<EditBookMetadataState?>(null)
  internal val state: State<EditBookMetadataState?> get() = _state

  override suspend fun items(bookId: BookId): List<BottomSheetItem> {
    return listOf(BottomSheetItem.EditMetadata)
  }

  override suspend fun onItemClick(
    bookId: BookId,
    item: BottomSheetItem,
  ) {
    if (item != BottomSheetItem.EditMetadata) return
    val book = repo.get(bookId) ?: return
    _state.value = EditBookMetadataState(
      bookId = bookId,
      title = book.content.name,
      author = book.content.author ?: "",
      narrator = book.content.narrator ?: "",
      series = book.content.series ?: "",
      part = book.content.part ?: "",
      genre = book.content.genre ?: "",
    )
  }

  internal fun onDismiss() {
    _state.value = null
  }

  internal fun onUpdateTitle(title: String) {
    _state.value = _state.value?.copy(title = title)
  }

  internal fun onUpdateAuthor(author: String) {
    _state.value = _state.value?.copy(author = author)
  }

  internal fun onUpdateNarrator(narrator: String) {
    _state.value = _state.value?.copy(narrator = narrator)
  }

  internal fun onUpdateSeries(series: String) {
    _state.value = _state.value?.copy(series = series)
  }

  internal fun onUpdatePart(part: String) {
    _state.value = _state.value?.copy(part = part)
  }

  internal fun onUpdateGenre(genre: String) {
    _state.value = _state.value?.copy(genre = genre)
  }

  internal fun onConfirm() {
    val state = _state.value
    if (state != null) {
      check(state.confirmButtonEnabled)
      scope.launch {
        repo.updateBook(state.bookId) { content ->
          content.copy(
            name = state.title.trim(),
            author = state.author.trim().takeIf { it.isNotEmpty() },
            narrator = state.narrator.trim().takeIf { it.isNotEmpty() },
            series = state.series.trim().takeIf { it.isNotEmpty() },
            part = state.part.trim().takeIf { it.isNotEmpty() },
            genre = state.genre.trim().takeIf { it.isNotEmpty() },
          )
        }
      }
    }
    _state.value = null
  }
}
