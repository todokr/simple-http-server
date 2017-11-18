# 設定ファイルの読み込み

+++

## やりたいこと
- 設定ファイルの内容からMIMEを決定したい
  - apache/nginxのmime.typesみたいな

+++

```
# MIME type                 Extensions

text/csv                    csv
text/directory
text/dns
text/enriched
text/html                   html htm
text/parityfec
text/plain                  txt text conf def list log in
text/prs.fallenstein.rst
text/prs.lines.tag          dsc
text/red
text/rfc822-headers
text/richtext               rtx
```

+++

### 今回の設定ファイルの形式
- Java  
properties

- Scala  
mime.types

- Clojure  
edn

+++

## Java
- XML
- properties
- JSON/HOCON|
- yaml|

+++?code=java-simple-http-server/src/main/java/MimeDetector.java&lang=java

@[11](ファイルをImputStreamに)
@[12](propertiesオブジェクトの生成)
@[14](InputStreamをロード)
@[25-29](PathからMIMEを決定)
@[28](`props.getProperty(key, defalt);`)

+++

## Scala

- propertiesなどでも良いが...|
- パーサコンビネータを使ってmime.typesをパースしてみる|

+++

### パーサコンビネータ?🤔

- パーサ(関数)を引数にとる高階関数
- 簡単なパーサを組み合わせていくことで、複雑な構文をパースするパーサを作ることができる

+++?code=scala-simple-http-server/build.sbt&lang=scala
@[8](scala-parser-combinatorsを依存関係に追加)

+++?code=scala-simple-http-server/src/main/scala/MimeDetector.scala&lang=scala
@[20](scala.util.parsing.combinator.RegexParserをextends)
@[22](`#`始まりのコメント行を読み飛ばすようoverride)

+++

```scala
// types {
//   text/html  htm html shtml;
// }

private def mime = """[\w\./+-]+""".r
private def ext = repsep("""[\w\./+-]+""".r, """\s""".r)
private def line = key ~ value <~ ";"
private def list = """types\s*\{""".r ~> rep(line) <~ "}"
```

@[5](keyはアルファベット+記号)
@[6](valueはアルファベット+記号を空白文字で区切った繰り返し)
@[7](lineはkeyに続いたvalue、そして`;`)
@[8](listはlineの繰り返しを"types {"と"}"で挟んだもの)


+++?code=scala-simple-http-server/src/main/scala/MimeDetector.scala&lang=scala
@[31](パースの実行)
@[32-33](パースの成功/失敗をパターンマッチ)

+++

## Clojure

### Edn（Extensible data notation）
- Clojureのコードのサブセット
  - Clojureのコードとして評価できる

```clojure
{:name "Fred" 
 :age 23}
```

https://github.com/edn-format/edn

+++?code=clojure-simple-http-server/src/clojure_simple_http_server/mime_detector.clj&lang=clojure
@[2](clojure.ednのインポート)
@[8](resourceディレクトリのファイルをjava.net.URLへ)
@[9](全部読んで文字列に)
@[10](パースしてMapに)
