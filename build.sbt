name := """sokoban"""

version := "1.0"

scalaVersion := "2.11.6"
javaOptions += "-Xms512M"
javaOptions += "-Xmx4G"
fork in Test := true
fork in run := true

libraryDependencies ++= Seq(
  "org.scalatest" %% "scalatest" % "2.2.4" % "test"
)
