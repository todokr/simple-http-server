package simplehttpserver

import java.nio.ByteBuffer
import java.nio.charset.StandardCharsets
import java.time.format.DateTimeFormatter
import java.time.{OffsetDateTime, ZoneOffset}

case class Response(status: Status, contentType: String, body: Array[Byte]) {

  import Response._

  def toBytes: Array[Byte] = {
    val now = OffsetDateTime.now(ZoneOffset.UTC)
    val response =
      s"""HTTP/1.1 ${status.value}
         |Date: ${rfc1123Formatter.format(now)}
         |Server: SimpleScalaHttpServer
         |Content-Type: $contentType
         |Content-Length: ${body.length.toString}
         |Connection: Close
         |
         |""".stripMargin

    val headerBytes = response.getBytes(StandardCharsets.UTF_8)
    val buff = ByteBuffer.allocate(headerBytes.length + body.length)
    buff.put(headerBytes)
    buff.put(body)
    buff.array()
  }
}

object Response {
  private val HtmlMime = "text/html;charset=utf8"

  val BadRequest: Response =
    Response(
      status = Status.BadRequest,
      contentType = HtmlMime,
      body = "Bad Request".getBytes(StandardCharsets.UTF_8)
    )

  val Forbidden: Response =
    Response(
      status = Status.Forbidden,
      contentType = HtmlMime,
      body = "Forbidden".getBytes(StandardCharsets.UTF_8)
    )

  val NotFound: Response =
    Response(
      status = Status.NotFound,
      contentType = HtmlMime,
      body = "Not Found".getBytes(StandardCharsets.UTF_8)
    )

  private val rfc1123Formatter = DateTimeFormatter.RFC_1123_DATE_TIME
}
