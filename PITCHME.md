# 新しいプログラミング言語の学び方
## HTTPサーバーを作って学ぶJava, Scala, Clojure

@todokr

+++

## 誰だ

<img src="assets/img/icon.jpg" width="200px" height="200px" />

- 田所 駿佑 @todokr
- 株式会社ビズリーチ Scalaエンジニア
- 求人検索エンジンのクローラー開発など
- Unicode Emojiが好き😂
- Emacsが好きなネオ老害

+++

## 新しいプログラミング言語を学ぶ

- スキルやプログラミングスタイルの幅を広げるため|
- 純粋な楽しみのため|

+++

## Hello World!

- まずやる儀式|
- その次はどうする?|

+++

## HTTPサーバーを作ってみるのがおすすめ！

+++

## 今日お話しすること

- なぜHTTPサーバー?
- Java, Scala, Clojureについて
- HTTPサーバーを作りながら学ぼう
  - プロジェクトの準備
  - Socketの扱い
  - Input/OutputStreamの扱い
  - 文字列/バイト列の扱い
  - 正規表現
  - 設定ファイルの読み込み
  - リソースの開放
  - 並列処理
- HTTPサーバーの次は?

+++

# なぜHTTPサーバー?

+++

## 新しい言語を学ぶなら...

- Hello world, FizzBuzzをやる
- 書籍を読む
- TODOアプリなど簡単な**Webサービス**を作る

+++

## しかし

+++

## WebサービスにはDBがある
- とりあえずH2で良いか...|
- MySQLWorkBenchで作ったDDLが流せない！|
- Docker?とかいうやつで?MySQLを動かすのがナウいんですかね?|
- docker exec...なんだっけ😇|

+++

## Webサービスには画面がある
- とりあえず適当にbootstrapで作るぞー！|
- Semantic UIのほうがイマドキっぽいらしい|
- Vue.jsを使うぞ！vue-cli...???|
- 生CSS辛いからSassにしよう。なおStylusは(ry|
- Webpack? babel-loader? なにそれおいしいの？|
- Divが中央に寄らない!!!|

+++

## それってやりたいことだっけ？

+++

## Don't shave that yak
- 別にDBを触りたいわけではない|
- 別に画面を作りたいわけではない|

+++

## そこでHTTPサーバーですよ

+++

## HTTPサーバーなら

- DBや画面を用意する手間なく、手軽に開発が始められる|
- 文字列操作、入出力、並列処理など、Webアプリケーション開発に欠かせない要素に触れられる|

---

# Scala, Clojureについて

+++

## Scala

- オブジェクト指向と関数型の統合による高い表現力|
  - オブジェクト指向の概念で関数型の機能を解釈|
  - case class = Value Object / 代数的データ型|
- Javaとの互換性|

+++

## Scalaってバリバリの関数型言語なんでしょ?

---?image=assets/img/fp-for-mortals.png&size=contain

+++

> Please, don't do this in Scala! — Martin Odersky

+++

- オブジェクト指向と関数型の**統合**
- 全ての値がオブジェクトであるなど、オブジェクト指向言語としても優れた側面

+++

## Clojure
- JVM上で動くLisp方言|
- ファーストクラスの関数、イミュータブルなデータ構造、再帰的なループ|
- Javaとの互換性|
- REPLを活かしたインタラクティブ/インクリメンタルな開発|
- 作者であるRich Hickeyの考え方|

+++

## Simplicity Matters

> 設計上の機敏さ (architectural agility)、つまり根本的にシンプルなシステムを構築することによって得られる機敏さ、は他の全ての機敏さを圧倒するというのが僕の主張です。どんな開発プロセスを使っているかは関係ありません。

http://eed3si9n.com/ja/simplicity-matters

+++

## Clojureと「Simple Made Easy」

http://boxofpapers.hatenablog.com/entry/simple_made_easy

---

## HTTPサーバーを作りながら学ぼう

1.プロジェクトの準備|
2.Socketの扱い|
3.Input/OutputStreamの扱い|
4.文字列/バイト列の扱い|
5.正規表現|
6.設定ファイルの読み込み|
7.リソースの開放|
8.並列処理|

+++

## その前に

HTTPサーバーのやっていることやざっくりアーキテクチャを知ろう

---

# HTTPサーバーについて

+++

## HTTPサーバーがやっていること

+++

## RFC

+++

## ざっくりアーキテクチャ

---

# レシピ#1 プロジェクトの準備

---

# レシピ#2 Socketの扱い

+++

## Socket

通信におけるエンドポイントを表現したデータモデル

> TCPコネクションのソケットペア(socket pair)は、コネクションの両方のエンドポイントを定義する、ローカルIPアドレス、ローカルTCPポート、リモートIPアドレス、およびリモートTCPポートの４つ組である。あるソケットペアは、インターネットの中の特定のコネクションを一意に識別する。

> 各エンドポイントを識別する２つの値、すなわち**IPアドレスとポート番号**は、多くの場合ソケット (socket) と呼ばれる。

> 「UNIXネットワークプログラミング」 第２版 Vol.1 p43

---

# レシピ#3 Input/OutputStreamの扱い

---

# レシピ#4 文字列/バイト列の扱い

---

# レシピ#5 正規表現

---

# レシピ#6 設定ファイルの読み込み

---

# レシピ#7 リソースの開放

---

# レシピ#8 並列処理

---

# HTTPサーバーの次は?

+++

## 「渋谷Java」

+++

## 「教養としてのScala」

- 12/21 @ ビズリーチ（渋谷）

+++

## 「Shibuya Lisp」
