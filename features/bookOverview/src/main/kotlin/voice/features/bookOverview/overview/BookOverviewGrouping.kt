package voice.features.bookOverview.overview

import androidx.annotation.StringRes
import voice.core.strings.R as StringsR

enum class BookOverviewGrouping(
  @StringRes val nameRes: Int,
) {
  COMPLETION_STATUS(
    nameRes = StringsR.string.book_grouping_status,
  ),
  AUTHOR(
    nameRes = StringsR.string.book_grouping_author,
  ),
  SERIES(
    nameRes = StringsR.string.book_grouping_series,
  ),
  FOLDER(
    nameRes = StringsR.string.book_grouping_folder,
  ),
}
