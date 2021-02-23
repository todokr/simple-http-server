package simplehttpserver

/** HTTPレスポンスのステータス */
sealed abstract class Status(val value: String)

object Status {
  case object Ok extends Status("200 OK")
  case object BadRequest extends Status("400 BadRequest")
  case object Forbidden extends Status("403 Forbidden")
  case object NotFound extends Status("404 NotFound")
}
