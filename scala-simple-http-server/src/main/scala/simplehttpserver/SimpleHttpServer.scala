package simplehttpserver

import java.io.Closeable
import java.net.ServerSocket
import java.util.concurrent.Executors

import scala.concurrent.ExecutionContext.fromExecutorService
import scala.concurrent.{ExecutionContext, Future}

object SimpleHttpServer {

  val Port = 8001

  def main(args: Array[String]): Unit = {

    val parser = new RequestParser
    val requestHandler = new RequestHandler
    val executor = Executors.newCachedThreadPool()
    implicit val ec: ExecutionContext = fromExecutorService(executor)

    val serverSocket = new ServerSocket(Port)
    println(s"HTTP Server Start! Listening at ${Port}!")

    sys.addShutdownHook {
      println("Shutting down HTTP Server...")
      serverSocket.close()
      executor.shutdown()
    }

    while (true) {
      val clientSocket = serverSocket.accept

      Future {
        using(clientSocket) { socket =>
          val in = socket.getInputStream
          val out = socket.getOutputStream
          val request = parser.fromInputStream(in)
          val response = requestHandler.handleRequest(request)
          out.write(response.toBytes)
          out.flush()
        }
      }
    }
  }

  private def using[A, R <: Closeable](resource: R)(f: R => A): A =
    try f(resource)
    finally resource.close()
}
