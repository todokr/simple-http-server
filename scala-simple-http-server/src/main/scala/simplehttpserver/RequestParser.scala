package simplehttpserver

import java.io.{BufferedReader, InputStream, InputStreamReader}

class RequestParser {
  import RequestParser._

  def fromInputStream(in: InputStream): ParsedRequest = {
    val reader = new BufferedReader(new InputStreamReader(in))
    val requestLine = reader.readLine()

    requestLine match {
      case RequestLinePattern(method, targetPath, httpVersion) =>
        ValidRequest(method, targetPath, httpVersion)
      case malformed => MalformedRequest(malformed)
    }
  }
}

object RequestParser {
  final val RequestLinePattern = "(.+) (.+) (.+)".r
}
