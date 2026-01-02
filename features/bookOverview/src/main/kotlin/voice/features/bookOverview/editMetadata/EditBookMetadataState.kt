package voice.features.bookOverview.editMetadata

import voice.core.data.BookId

internal data class EditBookMetadataState(
  val bookId: BookId,
  val title: String,
  val author: String,
  val narrator: String,
  val series: String,
  val part: String,
  val genre: String,
) {
  val confirmButtonEnabled: Boolean = title.trim().isNotEmpty()
}
