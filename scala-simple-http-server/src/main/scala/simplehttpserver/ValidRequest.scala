package simplehttpserver

sealed trait ParsedRequest

case class ValidRequest(method: String, targetPath: String, httpVersion: String)
    extends ParsedRequest
case class MalformedRequest(rawRequestLine: String) extends ParsedRequest
