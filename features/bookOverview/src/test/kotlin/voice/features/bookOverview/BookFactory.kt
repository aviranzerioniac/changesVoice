package voice.features.bookOverview

import voice.core.data.Book
import voice.core.data.BookContent
import voice.core.data.BookId
import voice.core.data.Chapter
import voice.core.data.ChapterId
import java.io.File
import java.time.Instant
import java.util.UUID

fun book(
  chapters: List<Chapter> = listOf(chapter(), chapter()),
  time: Long = 42,
  currentChapter: ChapterId = chapters.first().id,
  name: String = UUID.randomUUID().toString(),
  author: String? = UUID.randomUUID().toString(),
  series: String? = null,
  cover: File? = null,
): Book {
  return Book(
    content = BookContent(
      author = author,
      name = name,
      positionInChapter = time,
      playbackSpeed = 1F,
      addedAt = Instant.EPOCH,
      chapters = chapters.map { it.id },
      cover = cover,
      currentChapter = currentChapter,
      isActive = true,
      lastPlayedAt = Instant.EPOCH,
      skipSilence = false,
      id = BookId(UUID.randomUUID().toString()),
      gain = 0F,
      genre = null,
      narrator = null,
      series = series,
      part = null,
    ),
    chapters = chapters,
  )
}

fun chapter(
  duration: Long = 10000,
  id: ChapterId = ChapterId(UUID.randomUUID().toString()),
): Chapter {
  return Chapter(
    id = id,
    name = UUID.randomUUID().toString(),
    duration = duration,
    fileLastModified = Instant.EPOCH,
    markData = emptyList(),
  )
}
