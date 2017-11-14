import java.nio.file.{Files, Paths}

import enums._

object RequestHandler {
  val BadRequestHtmlPath = Paths.get("public/400.html")
  val ForbiddenHtmlPath = Paths.get("public/403.html")
  val NotFoundHtmlPath = Paths.get("public/404.html")
  val HtmlMime = "text/html;charset=utf8"
  val mimeDetector = new MimeDetector("mime.types")

  def handleRequest(request: Request): Response = {
    val normalizedPath = Paths.get("public", request.targetPath).normalize
    val path = if (Files.isDirectory(normalizedPath)) normalizedPath.resolve("index.html") else normalizedPath
    
    if (!path.startsWith("public/")) {
      Response(Forbidden, HtmlMime, Files.readAllBytes(ForbiddenHtmlPath))
    } else if (!Files.exists(path)) {
      Response(NotFound, HtmlMime, Files.readAllBytes(NotFoundHtmlPath))
    } else {
      Response(Ok, mimeDetector.getMime(path.getFileName.toString), Files.readAllBytes(path))
    }
  }
}