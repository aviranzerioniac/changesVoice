package voice.features.bookOverview.di

import androidx.datastore.core.DataStore
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Scope
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.Serializable
import voice.core.data.store.VoiceDataStoreFactory
import voice.features.bookOverview.bottomSheet.BottomSheetViewModel
import voice.features.bookOverview.deleteBook.DeleteBookViewModel
import voice.features.bookOverview.editTitle.EditBookTitleViewModel
import voice.features.bookOverview.fileCover.FileCoverViewModel
import voice.features.bookOverview.overview.BookFilterStore
import voice.features.bookOverview.overview.BookGroupingStore
import voice.features.bookOverview.overview.BookOverviewGrouping
import voice.features.bookOverview.overview.BookOverviewViewModel
import voice.features.bookOverview.overview.BookSortOption
import voice.features.bookOverview.overview.BookSortStore
import voice.features.bookOverview.overview.BookFilterOption

@Scope
annotation class BookOverviewScope

@GraphExtension(scope = BookOverviewScope::class)
@BookOverviewScope
interface BookOverviewGraph {
  val bookOverviewViewModel: BookOverviewViewModel
  val editBookTitleViewModel: EditBookTitleViewModel
  val bottomSheetViewModel: BottomSheetViewModel
  val deleteBookViewModel: DeleteBookViewModel
  val fileCoverViewModel: FileCoverViewModel

  @GraphExtension.Factory
  @ContributesTo(AppScope::class)
  interface Factory {
    fun create(): BookOverviewGraph

    @Provides
    @SingleIn(AppScope::class)
    @BookGroupingStore
    fun bookGroupingStore(factory: VoiceDataStoreFactory): DataStore<BookOverviewGrouping> {
      return factory.create(
        serializer = BookOverviewGroupingSerializer,
        fileName = "BookGrouping",
        defaultValue = BookOverviewGrouping.COMPLETION_STATUS,
      )
    }

    @Provides
    @SingleIn(AppScope::class)
    @BookSortStore
    fun bookSortStore(factory: VoiceDataStoreFactory): DataStore<BookSortOption> {
      return factory.create(
        serializer = BookSortOptionSerializer,
        fileName = "BookSort",
        defaultValue = BookSortOption.ALPHABETICAL,
      )
    }

    @Provides
    @SingleIn(AppScope::class)
    @BookFilterStore
    fun bookFilterStore(factory: VoiceDataStoreFactory): DataStore<BookFilterOption> {
      return factory.create(
        serializer = BookFilterOptionSerializer,
        fileName = "BookFilter",
        defaultValue = BookFilterOption.ALL,
      )
    }

    @ContributesTo(AppScope::class)
    interface Provider {
      val bookOverviewGraphProviderFactory: Factory
    }
  }
}

@Serializable
private enum class BookOverviewGroupingSerializable {
  COMPLETION_STATUS,
  AUTHOR,
  SERIES,
  FOLDER,
}

private object BookOverviewGroupingSerializer :
  voice.core.common.serialization.EnumSerializer<BookOverviewGrouping, BookOverviewGroupingSerializable>(
    values = BookOverviewGrouping.entries.toTypedArray(),
    serialize = { value ->
      when (value) {
        BookOverviewGrouping.COMPLETION_STATUS -> BookOverviewGroupingSerializable.COMPLETION_STATUS
        BookOverviewGrouping.AUTHOR -> BookOverviewGroupingSerializable.AUTHOR
        BookOverviewGrouping.SERIES -> BookOverviewGroupingSerializable.SERIES
        BookOverviewGrouping.FOLDER -> BookOverviewGroupingSerializable.FOLDER
      }
    },
    deserialize = { serialized ->
      when (serialized) {
        BookOverviewGroupingSerializable.COMPLETION_STATUS -> BookOverviewGrouping.COMPLETION_STATUS
        BookOverviewGroupingSerializable.AUTHOR -> BookOverviewGrouping.AUTHOR
        BookOverviewGroupingSerializable.SERIES -> BookOverviewGroupingSerializable.SERIES
        BookOverviewGroupingSerializable.FOLDER -> BookOverviewGrouping.FOLDER
      }
    },
  )

@Serializable
private enum class BookSortOptionSerializable {
  ALPHABETICAL,
  RECENTLY_ADDED,
  RECENTLY_PLAYED,
  DURATION,
}

private object BookSortOptionSerializer :
  voice.core.common.serialization.EnumSerializer<BookSortOption, BookSortOptionSerializable>(
    values = BookSortOption.entries.toTypedArray(),
    serialize = { value ->
      when (value) {
        BookSortOption.ALPHABETICAL -> BookSortOptionSerializable.ALPHABETICAL
        BookSortOption.RECENTLY_ADDED -> BookSortOptionSerializable.RECENTLY_ADDED
        BookSortOption.RECENTLY_PLAYED -> BookSortOptionSerializable.RECENTLY_PLAYED
        BookSortOption.DURATION -> BookSortOptionSerializable.DURATION
      }
    },
    deserialize = { serialized ->
      when (serialized) {
        BookSortOptionSerializable.ALPHABETICAL -> BookSortOption.ALPHABETICAL
        BookSortOptionSerializable.RECENTLY_ADDED -> BookSortOption.RECENTLY_ADDED
        BookSortOptionSerializable.RECENTLY_PLAYED -> BookSortOption.RECENTLY_PLAYED
        BookSortOptionSerializable.DURATION -> BookSortOption.DURATION
      }
    },
  )

@Serializable
private enum class BookFilterOptionSerializable {
  ALL,
  CURRENT_ONLY,
  NOT_STARTED_ONLY,
  COMPLETED_ONLY,
}

private object BookFilterOptionSerializer :
  voice.core.common.serialization.EnumSerializer<BookFilterOption, BookFilterOptionSerializable>(
    values = BookFilterOption.entries.toTypedArray(),
    serialize = { value ->
      when (value) {
        BookFilterOption.ALL -> BookFilterOptionSerializable.ALL
        BookFilterOption.CURRENT_ONLY -> BookFilterOptionSerializable.CURRENT_ONLY
        BookFilterOption.NOT_STARTED_ONLY -> BookFilterOptionSerializable.NOT_STARTED_ONLY
        BookFilterOption.COMPLETED_ONLY -> BookFilterOptionSerializable.COMPLETED_ONLY
      }
    },
    deserialize = { serialized ->
      when (serialized) {
        BookFilterOptionSerializable.ALL -> BookFilterOption.ALL
        BookFilterOptionSerializable.CURRENT_ONLY -> BookFilterOption.CURRENT_ONLY
        BookFilterOptionSerializable.NOT_STARTED_ONLY -> BookFilterOption.NOT_STARTED_ONLY
        BookFilterOptionSerializable.COMPLETED_ONLY -> BookFilterOption.COMPLETED_ONLY
      }
    },
  )
