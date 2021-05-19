package simplehttpserver

import java.io.{BufferedReader, InputStream, InputStreamReader}

class RequestParser {
  import RequestParser._

  def fromInputStream(in: InputStream): Option[Request] = {
    val reader = new BufferedReader(new InputStreamReader(in))
    val rawRequestLine = reader.readLine()

    Option(rawRequestLine).collect {
      case RequestLinePattern(method, targetPath, httpVersion) =>
        Request(method, targetPath, httpVersion)
    }
  }
}

object RequestParser {
  final val RequestLinePattern = "(.+) (.+) (.+)".r
}
