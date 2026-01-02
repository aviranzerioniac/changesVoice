package voice.core.metadata.suggester

import androidx.datastore.core.DataStore
import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.BindingContainer
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.Provides
import dev.zacsweers.metro.SingleIn
import kotlinx.serialization.KSerializer
import kotlinx.serialization.descriptors.PrimitiveKind
import kotlinx.serialization.descriptors.PrimitiveSerialDescriptor
import kotlinx.serialization.descriptors.SerialDescriptor
import kotlinx.serialization.encoding.Decoder
import kotlinx.serialization.encoding.Encoder
import voice.core.data.store.VoiceDataStoreFactory
import javax.inject.Qualifier

@Qualifier
annotation class FolderStructurePatternStore

@ContributesTo(AppScope::class)
@BindingContainer
object FolderStructurePatternModule {

  @Provides
  @SingleIn(AppScope::class)
  @FolderStructurePatternStore
  fun folderStructurePatternStore(
    factory: VoiceDataStoreFactory,
  ): DataStore<FolderStructurePattern> {
    return factory.create(
      serializer = FolderStructurePatternSerializer,
      fileName = "FolderStructurePattern",
      defaultValue = FolderStructurePattern.AUTHOR_BOOK,
    )
  }
}

private object FolderStructurePatternSerializer : KSerializer<FolderStructurePattern> {
  override val descriptor: SerialDescriptor =
    PrimitiveSerialDescriptor("FolderStructurePattern", PrimitiveKind.STRING)

  override fun deserialize(decoder: Decoder): FolderStructurePattern {
    return when (decoder.decodeString()) {
      "BOOK_ONLY" -> FolderStructurePattern.BOOK_ONLY
      "AUTHOR_BOOK" -> FolderStructurePattern.AUTHOR_BOOK
      "SERIES_BOOK" -> FolderStructurePattern.SERIES_BOOK
      "AUTHOR_SERIES_BOOK" -> FolderStructurePattern.AUTHOR_SERIES_BOOK
      else -> FolderStructurePattern.AUTHOR_BOOK
    }
  }

  override fun serialize(encoder: Encoder, value: FolderStructurePattern) {
    val serialized = when (value) {
      FolderStructurePattern.BOOK_ONLY -> "BOOK_ONLY"
      FolderStructurePattern.AUTHOR_BOOK -> "AUTHOR_BOOK"
      FolderStructurePattern.SERIES_BOOK -> "SERIES_BOOK"
      FolderStructurePattern.AUTHOR_SERIES_BOOK -> "AUTHOR_SERIES_BOOK"
    }
    encoder.encodeString(serialized)
  }
}
