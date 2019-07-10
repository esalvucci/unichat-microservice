version := "1.0.0"

name := "unichat-server"

organization := "io.swagger"

scalaVersion := "2.11.8"

val playJsonDerivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % "3.3"

libraryDependencies ++= Seq(
  lagomScaladslApi,
  playJsonDerivedCodecs
)

lazy val unichat = (project in file("."))
  .aggregate(`member-api`, `member-impl`, `member-stream-api`, `member-stream-impl`)

lazy val `member-api` = (project in file("member-api"))
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomScaladslApi
  )


lazy val `member-impl` = (project in file("member-impl"))
  .enablePlugins(LagomScala)
  .settings(
    version := "1.0-SNAPSHOT"
  )
  .dependsOn(`member-api`)


lazy val `member-stream-api` = (project in file("member-stream-api"))
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomScaladslApi
  )

lazy val `member-stream-impl` = (project in file("member-stream-impl"))
  .enablePlugins(LagomScala)
  .settings(
    version := "1.0-SNAPSHOT"
  )
  .dependsOn(`member-stream-api`, `member-api`)