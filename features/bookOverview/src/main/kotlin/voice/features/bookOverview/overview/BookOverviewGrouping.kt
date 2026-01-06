package voice.features.bookOverview.overview

import androidx.annotation.StringRes
import voice.core.strings.R as StringsR

enum class BookOverviewGrouping(
  @StringRes val nameRes: Int,
) {
  AUTHOR(
    nameRes = StringsR.string.book_grouping_author,
  ),
  FOLDER(
    nameRes = StringsR.string.book_grouping_folder,
  ),
  SERIES(
    nameRes = StringsR.string.book_grouping_series,
  ),
}
