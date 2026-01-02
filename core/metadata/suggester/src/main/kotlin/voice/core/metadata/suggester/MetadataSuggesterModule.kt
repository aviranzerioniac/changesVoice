package voice.core.metadata.suggester

import dev.zacsweers.metro.AppScope
import dev.zacsweers.metro.ContributesTo
import dev.zacsweers.metro.IntoSet
import dev.zacsweers.metro.Provides

@ContributesTo(AppScope::class)
interface MetadataSuggesterModule {

  @Provides
  @IntoSet
  fun provideMetadataSuggester(impl: MetadataSuggesterImpl): MetadataSuggester = impl
}
