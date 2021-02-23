package simplehttpserver

import java.io.{BufferedReader, InputStream, InputStreamReader}

class RequestParser {

  def fromInputStream(in: InputStream): Option[Request] = {
    val reader = new BufferedReader(new InputStreamReader(in))
    val requestLine = reader.readLine()

    // requestLineはnullかも知れないのでOptionでくるむ
    Option(requestLine).map { line =>
      // 変数x, y, zにArrayの中身を束縛。Arrayの長さが3ではない場合は例外をスローする
      val Array(method, targetPath, httpVersion) = line.split("\\s")
      Request(method, targetPath, httpVersion)
    }
  }
}
