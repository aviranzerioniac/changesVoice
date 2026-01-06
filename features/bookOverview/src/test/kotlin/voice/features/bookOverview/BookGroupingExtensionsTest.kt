package voice.features.bookOverview

import io.kotest.matchers.collections.shouldContainExactly
import io.kotest.matchers.shouldBe
import org.junit.Test
import java.io.File
import voice.core.data.Book
import voice.features.bookOverview.overview.BookFilterOption
import voice.features.bookOverview.overview.BookOverviewGrouping
import voice.features.bookOverview.overview.BookOverviewItemViewState
import voice.features.bookOverview.overview.BookSortOption
import voice.features.bookOverview.overview.groupByStrategy

class BookGroupingExtensionsTest {

  @Test
  fun `falls back to author folder when metadata missing`() {
    val books = listOf(
      book(
        author = null,
        series = null,
        cover = File("Author/Series/Book/cover.jpg"),
      ),
    )

    val grouped = books.groupByStrategy(
      grouping = BookOverviewGrouping.AUTHOR,
      sortOption = BookSortOption.ALPHABETICAL,
      filterOption = BookFilterOption.ALL,
      toItemViewState = toViewState,
    )

    grouped.map { it.groupName } shouldContainExactly listOf("Author")
  }

  @Test
  fun `falls back to series folder when metadata missing`() {
    val books = listOf(
      book(
        author = null,
        series = null,
        cover = File("Author/Series/Book/cover.jpg"),
      ),
    )

    val grouped = books.groupByStrategy(
      grouping = BookOverviewGrouping.SERIES,
      sortOption = BookSortOption.ALPHABETICAL,
      filterOption = BookFilterOption.ALL,
      toItemViewState = toViewState,
    )

    grouped.map { it.groupName } shouldContainExactly listOf("Series")
  }

  @Test
  fun `series falls back to none when only author folder exists`() {
    val books = listOf(
      book(
        author = null,
        series = null,
        cover = File("Author/Book/cover.jpg"),
      ),
    )

    val grouped = books.groupByStrategy(
      grouping = BookOverviewGrouping.SERIES,
      sortOption = BookSortOption.ALPHABETICAL,
      filterOption = BookFilterOption.ALL,
      toItemViewState = toViewState,
    )

    grouped.single().groupName shouldBe "No Series"
  }

  @Test
  fun `normalizes whitespace in folder names`() {
    val books = listOf(
      book(
        author = null,
        series = null,
        cover = File("  Author   Name /  Series   Name /Book/cover.jpg"),
      ),
    )

    val authors = books.groupByStrategy(
      grouping = BookOverviewGrouping.AUTHOR,
      sortOption = BookSortOption.ALPHABETICAL,
      filterOption = BookFilterOption.ALL,
      toItemViewState = toViewState,
    )
    authors.single().groupName shouldBe "Author Name"

    val series = books.groupByStrategy(
      grouping = BookOverviewGrouping.SERIES,
      sortOption = BookSortOption.ALPHABETICAL,
      filterOption = BookFilterOption.ALL,
      toItemViewState = toViewState,
    )
    series.single().groupName shouldBe "Series Name"
  }

  @Test
  fun `keeps metadata when present`() {
    val books = listOf(
      book(
        author = "Metadata Author",
        series = "Metadata Series",
        cover = File("Folder Author/Folder Series/Book/cover.jpg"),
      ),
    )

    val authors = books.groupByStrategy(
      grouping = BookOverviewGrouping.AUTHOR,
      sortOption = BookSortOption.ALPHABETICAL,
      filterOption = BookFilterOption.ALL,
      toItemViewState = toViewState,
    )
    authors.single().groupName shouldBe "Metadata Author"

    val series = books.groupByStrategy(
      grouping = BookOverviewGrouping.SERIES,
      sortOption = BookSortOption.ALPHABETICAL,
      filterOption = BookFilterOption.ALL,
      toItemViewState = toViewState,
    )
    series.single().groupName shouldBe "Metadata Series"
  }

  private val toViewState: (Book) -> BookOverviewItemViewState = { book ->
    BookOverviewItemViewState(
      name = book.content.name,
      author = book.content.author,
      cover = null,
      progress = 0F,
      id = book.id,
      remainingTime = "",
    )
  }
}
