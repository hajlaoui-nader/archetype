import xyz.funnycoding.sbt._

name := "exo"

version := "0.1"

scalaVersion := "2.12.8"
name := "exo"

lazy val exo = (project in file("."))
    .enablePlugins(BuildInfoPlugin)
    .settings(scalacOptions ++= ScalaC.commonScalaCOptions)
    .settings(libraryDependencies ++= Dependencies.all)