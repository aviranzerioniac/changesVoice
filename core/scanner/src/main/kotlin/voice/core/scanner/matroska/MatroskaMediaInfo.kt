package voice.core.scanner.matroska

import voice.core.data.MarkData

public data class MatroskaMediaInfo(
  val album: String? = null,
  val artist: String? = null,
  val title: String? = null,
  val chapters: List<MarkData> = emptyList(),
)
