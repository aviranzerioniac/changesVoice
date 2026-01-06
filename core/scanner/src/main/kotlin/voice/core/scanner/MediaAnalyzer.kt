package voice.core.scanner

import voice.core.documentfile.CachedDocumentFile

public interface MediaAnalyzer {
  public suspend fun analyze(file: CachedDocumentFile): Metadata?
}
