name := "play2"

version := "1.0-SNAPSHOT"

lazy val root = (project in file(".")).enablePlugins(PlayScala)

libraryDependencies ++= Seq(
  jdbc,
  anorm,
  cache,
  ws,
  "mysql" % "mysql-connector-java" % "5.1.24",
  "com.typesafe" %% "scalalogging-slf4j" % "1.0.0",
  "org.slf4j" % "slf4j-api" % "1.7.1",
  "ch.qos.logback" % "logback-classic" % "1.0.7"
)     

pipelineStages := Seq(rjs, digest, gzip)
