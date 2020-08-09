version := "1.0.0"

name := "unichat-microservice"

organization := "io.swagger"

scalaVersion in ThisBuild:= "2.11.12"
lagomServiceGatewayImpl in ThisBuild := "akka-http"

val playJsonDerivedCodecs = "org.julienrf" %% "play-json-derived-codecs" % "3.3"
val macwire = "com.softwaremill.macwire" %% "macros" % "2.3.0" % "provided"
val scalaTest = "org.scalatest" %% "scalatest" % "3.0.4" % Test

lagomCassandraCleanOnStart in ThisBuild := true

lazy val unichat = (project in file("."))
  .aggregate(`member-api`, `member-impl`)

lazy val `member-api` = (project in file("member-api"))
  .settings(
    version := "1.0-SNAPSHOT",
    libraryDependencies += lagomScaladslApi
  )


lazy val `member-impl` = (project in file("member-impl"))
  .enablePlugins(LagomScala)
  .settings(
    libraryDependencies ++= Seq(
      lagomScaladslCluster,
      lagomScaladslPersistenceCassandra,
      lagomScaladslKafkaBroker,
      lagomScaladslTestKit,
      macwire,
      scalaTest
    )
  )
  .dependsOn(`member-api`)
