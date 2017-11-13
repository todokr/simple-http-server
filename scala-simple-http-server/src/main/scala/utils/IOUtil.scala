package utils

import java.io.Closeable

object IOUtil {

  def using[A, R <: Closeable](resource: R)(f: R => A): A = {
    try {
      f(resource)
    } finally {
      resource.close()
    }
  }
}
