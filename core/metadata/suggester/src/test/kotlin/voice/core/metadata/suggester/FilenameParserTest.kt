package voice.core.metadata.suggester

import io.kotest.matchers.nulls.shouldBeNull
import io.kotest.matchers.nulls.shouldNotBeNull
import io.kotest.matchers.shouldBe
import org.junit.Test

class FilenameParserTest {

  private val parser = FilenameParser()

  @Test
  fun `parse author dash title`() {
    val result = parser.parse("J.K. Rowling - Harry Potter and the Philosopher's Stone.mp3")
    
    result.author shouldBe "J.K. Rowling"
    result.title shouldBe "Harry Potter and the Philosopher's Stone"
    result.narrator.shouldBeNull()
  }

  @Test
  fun `parse bracket author with narrator`() {
    val result = parser.parse("[Stephen King] The Stand (Grover Gardner).m4b")
    
    result.author shouldBe "Stephen King"
    result.title shouldBe "The Stand"
    result.narrator shouldBe "Grover Gardner"
  }

  @Test
  fun `parse title with narrator in parentheses`() {
    val result = parser.parse("1984 (Simon Prebble).m4b")
    
    result.author.shouldBeNull()
    result.title shouldBe "1984"
    result.narrator shouldBe "Simon Prebble"
  }

  @Test
  fun `parse series with book number`() {
    val result = parser.parse("J.R.R. Tolkien - The Lord of the Rings 01.mp3")
    
    result.author shouldBe "J.R.R. Tolkien"
    result.title shouldBe "The Lord of the Rings 01"
    result.series.shouldNotBeNull()
    result.series.name shouldBe "The Lord of the Rings"
    result.series.part shouldBe "01"
  }

  @Test
  fun `parse series with Part prefix`() {
    val result = parser.parse("Foundation Part 2.mp3")
    
    result.title shouldBe "Foundation Part 2"
    result.series.shouldNotBeNull()
    result.series.name shouldBe "Foundation"
    result.series.part shouldBe "2"
  }

  @Test
  fun `parse series with volume`() {
    val result = parser.parse("Dune Vol 3.mp3")
    
    result.title shouldBe "Dune Vol 3"
    result.series.shouldNotBeNull()
    result.series.name shouldBe "Dune"
    result.series.part shouldBe "3"
  }

  @Test
  fun `parse series with book keyword`() {
    val result = parser.parse("The Expanse Book 5.mp3")
    
    result.title shouldBe "The Expanse Book 5"
    result.series.shouldNotBeNull()
    result.series.name shouldBe "The Expanse"
    result.series.part shouldBe "5"
  }

  @Test
  fun `parse author dash series dash title`() {
    val result = parser.parse("Brandon Sanderson - Mistborn - The Final Empire.mp3")

    result.author shouldBe "Brandon Sanderson"
    result.title shouldBe "The Final Empire"
    result.series.shouldNotBeNull()
    result.series.name shouldBe "Mistborn"
    result.series.part.shouldBeNull()
  }

  @Test
  fun `parse underscores and en dash`() {
    val result = parser.parse("N_K_Jemisin – The_Broken_Earth - Book 1.m4b")

    result.author shouldBe "N K Jemisin"
    result.title shouldBe "Book 1"
    result.series.shouldNotBeNull()
    result.series.name shouldBe "The Broken Earth"
    result.series.part shouldBe "1"
  }

  @Test
  fun `fallback to full filename as title`() {
    val result = parser.parse("Some Random Audiobook.mp3")
    
    result.author.shouldBeNull()
    result.title shouldBe "Some Random Audiobook"
    result.narrator.shouldBeNull()
  }
}
