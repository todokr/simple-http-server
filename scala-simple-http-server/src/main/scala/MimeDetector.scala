import java.nio.charset.StandardCharsets
import java.nio.file.{Files, Path, Paths}

import scala.collection.JavaConverters._
import scala.util.matching.Regex
import scala.util.parsing.combinator._

class MimeDetector(configName: String) {

  private val mimeMap = MimeConfParser.parse(Paths.get(Thread.currentThread.getContextClassLoader.getResource(configName).toURI))
  private val extPattern = """(?<=\.)[A-Za-z0-9]+$""".r

  def getMime(fileName: String): String =
    extPattern.findFirstIn(fileName) // Regex#findFirstInはOptionを返す
      .flatMap(ext => mimeMap.get(ext)) // Map#getもOptionを返す。なのでflatMapする
      .getOrElse("application/octet-stream")
}

object MimeConfParser extends RegexParsers {

  override val whiteSpace: Regex = """(\s|#.*)+""".r

  // values
  private def key = """[\w\./+-]+""".r
  private def value = repsep("""[\w\./+-]+""".r, """\s?""".r)
  private def line = key ~ value <~ ";"
  private def list = """types\s*\{""".r ~> rep(line) <~ "}"

  def parse(conf: Path): Map[String, String] = {
    val in = Files.readAllLines(conf, StandardCharsets.UTF_8).asScala.mkString("\n")
    parseAll(list, in) match {
      case Success(result, _) => result.flatMap { case MimeConfParser.~(mime, exts) => exts.map(_ -> mime) }.toMap
      case NoSuccess(msg, next) => throw new ConfigParseException(s"Invalid config: $msg on line ${next.pos.line} on column ${next.pos.column}")
    }
  }

  class ConfigParseException(msg: String) extends RuntimeException(msg)
}
