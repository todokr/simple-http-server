object SimpleHttpServer {

  import java.net.ServerSocket

  import scala.concurrent.Future
  import scala.concurrent.ExecutionContext.Implicits.global
  import utils.IOUtil.using

  val Port = 8080

  def main(args: Array[String]): Unit = {

    val serverSocket = new ServerSocket(Port)
    val parser = new RequestParser
    val handler = new RequestHandler

    println("HTTP Server Start! Listening at " + Port + "!")

    while (true) {
      val socket = serverSocket.accept

      Future {
        using(socket) { s =>
          val in = s.getInputStream
          val out = s.getOutputStream

          val request = parser.fromInputStream(in)
          println(request)
          val response = request.map(handler.handleRequest)
          response.foreach(_.writeTo(out))
        }
      }
    }
  }
}
