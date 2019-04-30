package xyz.funnycoding.sbt

import sbt._

object Dependencies {
  // Functional libraries
  val catsVersion = "1.5.0"
  val catsEffectsVersion = "1.1.0"

  // JSON
  val circeVersion = "0.11.1"
  val circeDerivationVersion = "0.11.0-M1"

  // Test
  val scalaTestVersion = "3.0.5"

  val functionalDependencies = List(
    "org.typelevel" %% "cats-core" % catsVersion,
    "org.typelevel" %% "cats-effect" % catsEffectsVersion
  )

  val jsonDependencies = List(
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion,
    "io.circe" %% "circe-derivation" % circeDerivationVersion
  )

  val testDependencies = List(
    "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )

  val all = functionalDependencies ++ jsonDependencies ++ testDependencies

}
