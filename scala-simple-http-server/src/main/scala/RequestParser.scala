import java.io.{BufferedReader, InputStream, InputStreamReader}

object RequestParser {

  def fromInputStream(in: InputStream): Option[Request] = {
    val reader = new BufferedReader(new InputStreamReader(in))
    val requestLine = reader.readLine()

    Option(requestLine).map { line => // requestLineはnullかも知れないのでOptionでくるむ
      val Array(method, targetPath, httpVersion) = line.split("\\s") // 変数x, y, zにArrayの中身を束縛。Arrayの長さが3ではない場合は例外をスローする
      Request(method, targetPath, httpVersion)
    }
  }
}

case class Request(
  method: String,
  targetPath: String,
  httpVersion: String)
