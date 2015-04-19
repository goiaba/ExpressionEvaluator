name := "cs372s15p3"

version := "1.0"

scalaVersion := "2.11.5"

libraryDependencies ++= Seq(
  "org.parboiled" %% "parboiled" % "2.0.1",
  "org.scalatest" %% "scalatest" % "2.2.4" % Test,
  "jline" % "jline" % "2.12.1"
)

ScoverageSbtPlugin.ScoverageKeys.coverageExcludedPackages := """.*Calculator.*"""

