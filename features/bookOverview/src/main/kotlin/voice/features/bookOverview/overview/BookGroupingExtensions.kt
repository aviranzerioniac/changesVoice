package voice.features.bookOverview.overview

import voice.core.data.Book
import java.io.File

internal data class GroupedBooks(
  val groupName: String,
  val books: Map<BookOverviewCategory, List<BookOverviewItemViewState>>,
)

internal fun List<Book>.groupByStrategy(
  grouping: BookOverviewGrouping,
  sortOption: BookSortOption,
  filterOption: BookFilterOption,
  toItemViewState: (Book) -> BookOverviewItemViewState,
): List<GroupedBooks> {
  val filteredBooks = if (filterOption == BookFilterOption.ALL) {
    this
  } else {
    filter { it.category.matchesFilter(filterOption) }
  }
  
  return when (grouping) {
    BookOverviewGrouping.COMPLETION_STATUS -> {
      listOf(
        GroupedBooks(
          groupName = "",
          books = filteredBooks.groupByCategory(sortOption, toItemViewState),
        ),
      )
    }
    BookOverviewGrouping.AUTHOR -> {
      filteredBooks
        .groupBy { it.content.author ?: "Unknown Author" }
        .map { (author, books) ->
          GroupedBooks(
            groupName = author,
            books = books.groupByCategory(sortOption, toItemViewState),
          )
        }
        .sortedBy { it.groupName }
    }
    BookOverviewGrouping.SERIES -> {
      filteredBooks
        .groupBy { it.content.series ?: "No Series" }
        .map { (series, books) ->
          GroupedBooks(
            groupName = series,
            books = books.groupByCategory(sortOption, toItemViewState),
          )
        }
        .sortedBy { it.groupName }
    }
    BookOverviewGrouping.FOLDER -> {
      filteredBooks
        .groupBy { 
          it.chapters.firstOrNull()?.file?.parent?.let { File(it).name } ?: "Unknown Folder"
        }
        .map { (folder, books) ->
          GroupedBooks(
            groupName = folder,
            books = books.groupByCategory(sortOption, toItemViewState),
          )
        }
        .sortedBy { it.groupName }
    }
  }
}

private fun List<Book>.groupByCategory(
  sortOption: BookSortOption,
  toItemViewState: (Book) -> BookOverviewItemViewState,
): Map<BookOverviewCategory, List<BookOverviewItemViewState>> {
  return groupBy { it.category }
    .mapValues { (_, books) ->
      books
        .sortedWith(sortOption.comparator)
        .map(toItemViewState)
    }
}
