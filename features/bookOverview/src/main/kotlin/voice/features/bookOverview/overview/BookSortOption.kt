package voice.features.bookOverview.overview

import androidx.annotation.StringRes
import voice.core.data.Book
import voice.core.strings.R as StringsR

enum class BookSortOption(
  @StringRes val nameRes: Int,
  val comparator: Comparator<Book>,
) {
  ALPHABETICAL(
    nameRes = StringsR.string.book_sort_alphabetical,
    comparator = compareBy { it.content.name.lowercase() },
  ),
  RECENTLY_ADDED(
    nameRes = StringsR.string.book_sort_recently_added,
    comparator = compareByDescending { it.content.addedAt },
  ),
  RECENTLY_PLAYED(
    nameRes = StringsR.string.book_sort_recently_played,
    comparator = compareByDescending { it.content.lastPlayedAt },
  ),
  DURATION(
    nameRes = StringsR.string.book_sort_duration,
    comparator = compareByDescending { it.duration },
  ),
}
