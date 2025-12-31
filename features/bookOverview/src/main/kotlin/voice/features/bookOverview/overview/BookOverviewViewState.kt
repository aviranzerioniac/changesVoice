package voice.features.bookOverview.overview

import androidx.compose.runtime.Immutable
import kotlinx.collections.immutable.ImmutableMap
import kotlinx.collections.immutable.persistentMapOf
import voice.features.bookOverview.search.BookSearchViewState

@Immutable
data class BookOverviewViewState(
  val books: ImmutableMap<BookOverviewCategory, List<BookOverviewItemViewState>>,
  val groupedBooks: List<GroupedBooks>,
  val grouping: BookOverviewGrouping,
  val sortOption: BookSortOption,
  val filterOption: BookFilterOption,
  val layoutMode: BookOverviewLayoutMode,
  val playButtonState: PlayButtonState?,
  val showAddBookHint: Boolean,
  val showSearchIcon: Boolean,
  val isLoading: Boolean,
  val searchActive: Boolean,
  val searchViewState: BookSearchViewState,
  val showStoragePermissionBugCard: Boolean,
  val currentlyReading: BookOverviewItemViewState?,
) {

  companion object {
    val Loading = BookOverviewViewState(
      books = persistentMapOf(),
      groupedBooks = emptyList(),
      grouping = BookOverviewGrouping.COMPLETION_STATUS,
      sortOption = BookSortOption.ALPHABETICAL,
      filterOption = BookFilterOption.ALL,
      layoutMode = BookOverviewLayoutMode.List,
      playButtonState = null,
      showAddBookHint = false,
      showSearchIcon = false,
      isLoading = true,
      searchActive = false,
      searchViewState = BookSearchViewState.EmptySearch(
        suggestedAuthors = emptyList(),
        recentQueries = emptyList(),
        query = "",
      ),
      showStoragePermissionBugCard = false,
      currentlyReading = null,
    )
  }

  enum class PlayButtonState {
    Playing,
    Paused,
  }
}
