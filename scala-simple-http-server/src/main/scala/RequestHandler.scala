import java.nio.file.{Files, Paths}

import enums._

class RequestHandler {

  import RequestHandler._

  def handleRequest(request: Request): Response = {

    val normalizedPath = PublicDirPath.resolve(request.targetPath).normalize
    val path = if (Files.isDirectory(normalizedPath)) normalizedPath.resolve(IndexFileName) else normalizedPath
    
    if (!path.startsWith(PublicDirPath)) {
      Response(Forbidden, HtmlMime, Files.readAllBytes(ForbiddenHtmlPath))
    } else if (!Files.exists(path)) {
      Response(NotFound, HtmlMime, Files.readAllBytes(NotFoundHtmlPath))
    } else {
      Response(Ok, MimeDetector.getMime(path.getFileName.toString), Files.readAllBytes(path))
    }
  }
}

object RequestHandler {
  val PublicDirPath = Paths.get("public").toAbsolutePath
  val BadRequestHtmlPath = PublicDirPath.resolve("400.html")
  val ForbiddenHtmlPath = PublicDirPath.resolve("403.html")
  val NotFoundHtmlPath = PublicDirPath.resolve("404.html")
  val IndexFileName = "index.html"
  val HtmlMime = "text/html;charset=utf8"
}