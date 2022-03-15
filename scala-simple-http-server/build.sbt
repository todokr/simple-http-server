lazy val root = (project in file(".")).settings(
  name := "simple-http-server",
  version := "1.0",
  scalaVersion := "2.13.8",
  fork in Global := true,
  libraryDependencies ++= Seq(
    "org.scala-lang.modules" %% "scala-parser-combinators" % "1.1.2"
  )
)
