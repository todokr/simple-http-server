
# Simple HTTP Server
言語学習用のシンプルなHTTPサーバーの実装例です。現在 Java, Scala, Clojureの実装があります。

## 各言語のバージョン
- Java 17
- Scala 2.13.8
- Clojure 1.10.3

## 仕様
- localhost:8080で待ち受け、HTTPリクエストを受けとり、HTTPレスポンスを返す
- 対応するHTTPリクエストメソッドは`GET`のみ
- リソースのMIMEは外部ファイルで設定できる
- リクエストをブロックしない（マルチスレッド方式）
- Keep-Aliveはしない
- HTTP Cacheはしない


## JVM
`OpenJDK 17.0.2`  
(JVMを切り替える際は、[SDKMAN](https://sdkman.io/)が便利です)

## 起動とURL

### Java

#### 起動
```
$ brew install maven # if you need
$ cd ./java-simple-http-server
$ mvn compile
$ mvn exec:java
```

#### URL
http://localhost:8000


### Scala

#### 起動
```
$ brew install sbt # if you need
$ cd ./scala-simple-http-server
$ sbt run
```

#### URL
http://localhost:8001

### Clojure

#### 起動
```
$ brew install leiningen # if you need
$ cd ./clojure-simple-http-server
$ lein run
```

#### URL
http://localhost:8002
