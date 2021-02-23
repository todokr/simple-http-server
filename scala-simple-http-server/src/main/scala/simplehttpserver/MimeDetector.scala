package simplehttpserver

import scala.io.Source
import scala.util.matching.Regex
import scala.util.parsing.combinator._

/**
  * 拡張子込みのファイル名からMIMEを決定する。
  * 判別のルールは設定ファイルから読み込む。
  */
class MimeDetector(configFileName: String) {
  private val ExtensionPattern = """(?<=\.)[A-Za-z0-9]+$""".r

  private val mimeConfig =
    MimeConfParser.parseLines(Source.fromResource(configFileName).getLines().toSeq)

  def getMime(fileName: String): String =
    ExtensionPattern
      .findFirstIn(fileName)
      .flatMap { extension =>
        mimeConfig.get(extension)
      }
      .getOrElse("application/octet-stream")
}

case class MimeConfig(lines: Seq[Line]) {

  def get(extension: String): Option[String] = lines.collectFirst {
    case MimeConfigLine(mimeType, ext) if ext == extension => mimeType
  }
}
sealed trait Line
case class MimeConfigLine(mimeType: String, ext: String) extends Line
case class CommentLine(comment: String) extends Line
case object EmptyLine extends Line

/** MIMEを決定する設定ファイルのパーサー */
object MimeConfParser extends RegexParsers {

  override val whiteSpace: Regex = """\s+""".r

  private def line: Parser[Line] = configLine | commentLine | emptyLine

  private def configLine: MimeConfParser.Parser[MimeConfigLine] =
    mimeTypeToken ~ extToken <~ ";" ^^ {
      case mimeType ~ ext => MimeConfigLine(mimeType, ext)
    }

  private def commentLine: MimeConfParser.Parser[CommentLine] =
    """#.*""".r ^^ { comment => CommentLine(comment) }

  private def emptyLine: MimeConfParser.Parser[EmptyLine.type] =
    "".r ^^ { _ => EmptyLine }

  private def mimeTypeToken = """[\w\./+-]+""".r
  private def extToken = """[\w\./+-]+""".r

  parse(line, "#hoe").get

  def parseLines(rawLines: Seq[String]): MimeConfig = {

    val lines = rawLines.map { rawLine =>
      parse(line, rawLine) match {
        case Success(line, _) => line
        case NoSuccess(msg, next) =>
          println(rawLine)
          throw new ConfigParseException(
            s"Invalid config: $msg on line ${next.pos.line} on column ${next.pos.column}"
          )
      }
    }
    MimeConfig(lines)
  }

  class ConfigParseException(msg: String) extends RuntimeException(msg)
}
