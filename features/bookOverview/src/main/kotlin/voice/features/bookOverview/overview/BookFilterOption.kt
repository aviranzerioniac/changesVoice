package voice.features.bookOverview.overview

import androidx.annotation.StringRes
import voice.core.strings.R as StringsR

enum class BookFilterOption(
  @StringRes val nameRes: Int,
) {
  ALL(
    nameRes = StringsR.string.book_filter_all,
  ),
  CURRENT_ONLY(
    nameRes = StringsR.string.book_filter_current_only,
  ),
  NOT_STARTED_ONLY(
    nameRes = StringsR.string.book_filter_not_started_only,
  ),
  COMPLETED_ONLY(
    nameRes = StringsR.string.book_filter_completed_only,
  ),
}

fun BookOverviewCategory.matchesFilter(filter: BookFilterOption): Boolean {
  return when (filter) {
    BookFilterOption.ALL -> true
    BookFilterOption.CURRENT_ONLY -> this == BookOverviewCategory.CURRENT
    BookFilterOption.NOT_STARTED_ONLY -> this == BookOverviewCategory.NOT_STARTED
    BookFilterOption.COMPLETED_ONLY -> this == BookOverviewCategory.FINISHED
  }
}
