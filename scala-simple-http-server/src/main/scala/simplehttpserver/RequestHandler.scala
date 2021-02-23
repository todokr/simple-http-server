package simplehttpserver

import java.nio.file.{Files, Paths}

class RequestHandler {
  private val mimeDetector = new MimeDetector("mime.types")

  def handleRequest(request: Request): Response = {
    val normalizedPublicPath = Paths.get("public", request.targetPath).normalize
    val path =
      if (Files.isDirectory(normalizedPublicPath))
        normalizedPublicPath.resolve("index.html")
      else normalizedPublicPath

    if (!path.startsWith("public/")) {
      Response.BadRequest
    } else if (!Files.exists(path)) {
      Response.NotFound
    } else {
      val contentType = mimeDetector.getMime(path.getFileName.toString)
      Response(Status.Ok, contentType, Files.readAllBytes(path))
    }
  }
}
