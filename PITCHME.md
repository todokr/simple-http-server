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

Hola!

---

Goodbye!

+++

Adiós!
