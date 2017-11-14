# 新しいプログラミング言語の学び方
## HTTPサーバーを作って学ぶJava, Scala, Clojure

@todokr

+++

### Hello!

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
@title[Some custom label]

Goodbye!

+++

Adiós!
