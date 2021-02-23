package simplehttpserver

import java.net.ServerSocket
import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext.fromExecutorService
import scala.concurrent.{ExecutionContext, Future}

object SimpleHttpServer {

  val Port = 8080

  def main(args: Array[String]): Unit = {

    val parser = new RequestParser
    val requestHandler = new RequestHandler
    val executor = Executors.newCachedThreadPool()
    implicit val ec: ExecutionContext = fromExecutorService(executor)

    val serverSocket = new ServerSocket(Port)
    println(s"HTTP Server Start! Listening at ${Port}!")

    while (true) {
      val socket = serverSocket.accept

      Future {
        val in = socket.getInputStream
        val out = socket.getOutputStream
        val request = parser.fromInputStream(in)
        val response = request.map(requestHandler.handleRequest)
        response.foreach(_.writeTo(out))
        socket.close()
      }
    }
    println("Shutting down HTTP Server...")
    executor.shutdown()
  }
}
