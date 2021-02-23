package simplehttpserver

/** HTTP Request */
case class Request(method: String, targetPath: String, httpVersion: String)
