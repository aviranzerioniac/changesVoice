package voice.features.bookOverview.metadataReview

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import voice.core.metadata.suggester.MetadataSuggestions
import voice.core.documentfile.CachedDocumentFile
import dev.zacsweers.metro.Inject

@Inject
class MetadataReviewViewModel() : ViewModel() {

  private val _state = MutableStateFlow<ReviewState>(ReviewState.Idle)
  val state: StateFlow<ReviewState> = _state.asStateFlow()

  fun review(
    file: CachedDocumentFile,
    suggestions: MetadataSuggestions,
  ) {
    _state.value = ReviewState.Reviewing(file, suggestions)
  }

  fun acceptSuggestions(acceptedMetadata: AcceptedMetadata) {
    _state.value = ReviewState.Accepted(acceptedMetadata)
  }

  fun dismiss() {
    _state.value = ReviewState.Dismissed
  }

  fun reset() {
    _state.value = ReviewState.Idle
  }
}

sealed class ReviewState {
  object Idle : ReviewState()
  data class Reviewing(
    val file: CachedDocumentFile,
    val suggestions: MetadataSuggestions,
  ) : ReviewState()
  data class Accepted(val metadata: AcceptedMetadata) : ReviewState()
  object Dismissed : ReviewState()
}
