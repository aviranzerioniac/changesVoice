package voice.core.metadata.suggester.api

import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import org.json.JSONObject
import java.net.HttpURLConnection
import java.net.URL
import java.net.URLEncoder

/**
 * Client for MusicBrainz API to search for audiobook metadata
 */
class MusicBrainzApiClient {

  private val baseUrl = "https://musicbrainz.org/ws/2"
  private val userAgent = "Voice Audiobook Player/1.0 (https://github.com/PaulWoitaschek/Voice)"

  suspend fun searchRecording(query: String, artist: String? = null): ApiSearchResult? = withContext(Dispatchers.IO) {
    try {
      val searchQuery = buildString {
        append(URLEncoder.encode(query, "UTF-8"))
        if (artist != null) {
          append("+AND+artist:")
          append(URLEncoder.encode(artist, "UTF-8"))
        }
      }

      val url = URL("$baseUrl/recording/?query=$searchQuery&fmt=json&limit=5")
      val connection = url.openConnection() as HttpURLConnection
      connection.requestMethod = "GET"
      connection.setRequestProperty("User-Agent", userAgent)
      connection.connectTimeout = 5000
      connection.readTimeout = 5000

      if (connection.responseCode == 200) {
        val response = connection.inputStream.bufferedReader().readText()
        parseRecordingResponse(response)
      } else {
        null
      }
    } catch (e: Exception) {
      null
    }
  }

  private fun parseRecordingResponse(json: String): ApiSearchResult? {
    try {
      val root = JSONObject(json)
      val recordings = root.optJSONArray("recordings") ?: return null
      
      if (recordings.length() == 0) return null

      val firstRecording = recordings.getJSONObject(0)
      val title = firstRecording.optString("title")
      
      val artistCredits = firstRecording.optJSONArray("artist-credit")
      val artist = if (artistCredits != null && artistCredits.length() > 0) {
        artistCredits.getJSONObject(0).optJSONObject("artist")?.optString("name")
      } else {
        null
      }

      return if (title.isNotBlank()) {
        ApiSearchResult(
          title = title,
          artist = artist,
          source = "MusicBrainz",
        )
      } else {
        null
      }
    } catch (e: Exception) {
      return null
    }
  }
}

data class ApiSearchResult(
  val title: String,
  val artist: String?,
  val source: String,
)
