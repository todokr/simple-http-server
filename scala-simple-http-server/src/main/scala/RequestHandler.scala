import java.nio.file.{Files, Path, Paths}

import Errors.{DirectoryTraversal, FileNotFound}
import enums._

class RequestHandler {
  import RequestHandler._

  def handleRequest(request: Request): Response =
    (for {
      safeRequest <- detectDirectoryTraversal(request)
      resourcePath <- resolveResource(safeRequest)
    } yield (mimeDetector.getMime(resourcePath), Files.readAllBytes(resourcePath))) match {
      case Right((mime, body))      => Response(Ok, mime, body)
      case Left(FileNotFound)       => Response(NotFound, HtmlMime, Files.readAllBytes(NotFoundHtmlPath))
      case Left(DirectoryTraversal) => Response(Forbidden, HtmlMime, Files.readAllBytes(ForbiddenHtmlPath))
    }
}

object RequestHandler {
  import Errors._

  val PublicDirPath = Paths.get("public").toAbsolutePath
  val BadRequestHtmlPath = PublicDirPath.resolve("400.html")
  val ForbiddenHtmlPath = PublicDirPath.resolve("403.html")
  val NotFoundHtmlPath = PublicDirPath.resolve("404.html")
  val IndexFileName = "index.html"
  val HtmlMime = "text/html;charset=utf8"

  val detectDirectoryTraversal: Request => Either[Error, Request] = ???
  val resolveResource: Request => Either[Error, Path] = ???

  val mimeDetector = new MimeDetector("mime.types")
}

object Errors {
  sealed trait Error
  case object DirectoryTraversal extends Error
  case object FileNotFound extends Error
}