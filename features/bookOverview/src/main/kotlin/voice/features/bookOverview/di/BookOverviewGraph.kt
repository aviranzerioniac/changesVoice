package voice.features.bookOverview.di

import androidx.datastore.core.DataStore
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.GraphExtension
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.Scope
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import kotlinx.serialization.Serializable
import voice.core.data.store.VoiceDataStoreFactory
import voice.features.bookOverview.bottomSheet.BottomSheetViewModel
import voice.features.bookOverview.deleteBook.DeleteBookViewModel
import voice.features.bookOverview.editMetadata.EditBookMetadataViewModel
import voice.features.bookOverview.editTitle.EditBookTitleViewModel
import voice.features.bookOverview.fileCover.FileCoverViewModel
import voice.features.bookOverview.overview.BookFilterStore
import voice.features.bookOverview.overview.BookGroupExpansionStore
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
  val editBookMetadataViewModel: EditBookMetadataViewModel
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

    @Provides
    @SingleIn(AppScope::class)
    @BookGroupExpansionStore
    fun bookGroupExpansionStore(factory: VoiceDataStoreFactory): DataStore<Set<String>> {
      return factory.createSet(
        fileName = "BookGroupExpansion",
      )
    }

    @ContributesTo(AppScope::class)
    interface Provider {
      val bookOverviewGraphProviderFactory: Factory
    }
  }
}

private object BookOverviewGroupingSerializer : KSerializer<BookOverviewGrouping> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("BookOverviewGrouping", PrimitiveKind.STRING)

  override fun deserialize(decoder: Decoder): BookOverviewGrouping {
    return when (decoder.decodeString()) {
      "COMPLETION_STATUS" -> BookOverviewGrouping.COMPLETION_STATUS
      "AUTHOR" -> BookOverviewGrouping.AUTHOR
      "SERIES" -> BookOverviewGrouping.SERIES
      "FOLDER" -> BookOverviewGrouping.FOLDER
      else -> BookOverviewGrouping.COMPLETION_STATUS
    }
  }

  override fun serialize(encoder: Encoder, value: BookOverviewGrouping) {
    val serialized = when (value) {
      BookOverviewGrouping.COMPLETION_STATUS -> "COMPLETION_STATUS"
      BookOverviewGrouping.AUTHOR -> "AUTHOR"
      BookOverviewGrouping.SERIES -> "SERIES"
      BookOverviewGrouping.FOLDER -> "FOLDER"
    }
    encoder.encodeString(serialized)
  }
}

private object BookSortOptionSerializer : KSerializer<BookSortOption> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("BookSortOption", PrimitiveKind.STRING)

  override fun deserialize(decoder: Decoder): BookSortOption {
    return when (decoder.decodeString()) {
      "ALPHABETICAL" -> BookSortOption.ALPHABETICAL
      "RECENTLY_ADDED" -> BookSortOption.RECENTLY_ADDED
      "RECENTLY_PLAYED" -> BookSortOption.RECENTLY_PLAYED
      "DURATION" -> BookSortOption.DURATION
      else -> BookSortOption.ALPHABETICAL
    }
  }

  override fun serialize(encoder: Encoder, value: BookSortOption) {
    val serialized = when (value) {
      BookSortOption.ALPHABETICAL -> "ALPHABETICAL"
      BookSortOption.RECENTLY_ADDED -> "RECENTLY_ADDED"
      BookSortOption.RECENTLY_PLAYED -> "RECENTLY_PLAYED"
      BookSortOption.DURATION -> "DURATION"
    }
    encoder.encodeString(serialized)
  }
}

private object BookFilterOptionSerializer : KSerializer<BookFilterOption> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("BookFilterOption", PrimitiveKind.STRING)

  override fun deserialize(decoder: Decoder): BookFilterOption {
    return when (decoder.decodeString()) {
      "ALL" -> BookFilterOption.ALL
      "CURRENT_ONLY" -> BookFilterOption.CURRENT_ONLY
      "NOT_STARTED_ONLY" -> BookFilterOption.NOT_STARTED_ONLY
      "COMPLETED_ONLY" -> BookFilterOption.COMPLETED_ONLY
      else -> BookFilterOption.ALL
    }
  }

  override fun serialize(encoder: Encoder, value: BookFilterOption) {
    val serialized = when (value) {
      BookFilterOption.ALL -> "ALL"
      BookFilterOption.CURRENT_ONLY -> "CURRENT_ONLY"
      BookFilterOption.NOT_STARTED_ONLY -> "NOT_STARTED_ONLY"
      BookFilterOption.COMPLETED_ONLY -> "COMPLETED_ONLY"
    }
    encoder.encodeString(serialized)
  }
}
