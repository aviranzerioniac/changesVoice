package voice.features.bookOverview.overview

import voice.core.data.Book
import java.io.File

data class GroupedBooks(
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
    BookOverviewGrouping.AUTHOR -> {
      filteredBooks
        .groupBy { it.authorOrFolderFallback() }
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
        .groupBy { it.seriesOrFolderFallback() }
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
          it.content.cover?.parentFile?.normalizedFolderName() ?: "Unknown Folder"
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

private fun Book.authorOrFolderFallback(): String {
  val author = content.author?.takeIf { it.isNotBlank() }
    ?.normalizeFolderLabel()
  if (author != null) return author

  val parents = folderHierarchyExcludingBook()
  val authorFolder = parents.lastOrNull()
  return authorFolder ?: "Unknown Author"
}

private fun Book.seriesOrFolderFallback(): String {
  val series = content.series?.takeIf { it.isNotBlank() }
    ?.normalizeFolderLabel()
  if (series != null) return series

  val parents = folderHierarchyExcludingBook()
  val seriesFolder = parents.dropLast(1).lastOrNull()
  return seriesFolder ?: "No Series"
}

private fun Book.folderHierarchyExcludingBook(): List<String> {
  val bookFolder = content.cover?.parentFile ?: return emptyList()
  val normalizedHierarchy = generateSequence(bookFolder) { it.parentFile }
    .mapNotNull { it.normalizedFolderName() }
    .toList()
  return normalizedHierarchy.drop(1)
}

private fun File.normalizedFolderName(): String? {
  return name.normalizeFolderLabel()
}

private fun String.normalizeFolderLabel(): String? {
  val trimmedSeparators = trimEnd('/', '\\')
  val normalizedWhitespace = trimmedSeparators.trim().replace(WHITESPACE_REGEX, " ")
  return normalizedWhitespace.takeIf { it.isNotEmpty() }
}

private val WHITESPACE_REGEX = "\\s+".toRegex()

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
