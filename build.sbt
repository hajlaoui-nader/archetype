import xyz.funnycoding.sbt._

name := "archetype"
version := "0.1"
scalaVersion := "2.12.8"

lazy val bm4Version = "0.3.0-M4"
lazy val kindProjectorVersion = "0.10.0"

lazy val exo = (project in file("."))
  .enablePlugins(BuildInfoPlugin)
  .enablePlugins(GatlingPlugin)
  .settings(scalacOptions ++= ScalaC.commonScalaCOptions)
  .settings(libraryDependencies ++= Dependencies.all)
  .settings(
    addCompilerPlugin("com.olegpy" %% "better-monadic-for" % bm4Version),
    addCompilerPlugin("org.typelevel" %% "kind-projector" % kindProjectorVersion)
  )


