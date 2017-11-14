Hello!

```scala
protected case object PublicAction {
  def apply(requestHandler: Request[AnyContent] => Future[Result]): Action[AnyContent] =
    apply(BodyParsers.parse.anyContent)(requestHandler)
  def apply[A](bodyParser: BodyParser[A])(requestHandler: Request[A] => Future[Result]): Action[A] = {
    Action.async(bodyParser)(req => requestHandler(req))
  }
}
```

+++

+++?code=scala-simple-http-server/src/main/scala/MimeDetector.scala&lang=scala

@[1-3](なにか)
@[4-6](あれこれ)
@[8](がくぶる)
@[14-16](このくらい長い文字は入るだろうか)

---

Goodbye!

+++

Adiós!
