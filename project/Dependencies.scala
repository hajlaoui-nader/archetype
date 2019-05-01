package xyz.funnycoding.sbt

import sbt._

object Dependencies {
  // Functional libraries
  lazy val catsVersion = "1.5.0"
  lazy val catsEffectsVersion = "1.1.0"

  // JSON
  lazy val circeVersion = "0.11.1"
  lazy val circeDerivationVersion = "0.11.0-M1"

  // CSV
  lazy val csvSideVersion = "1.0.0"
  lazy val kantanVersion = "0.5.0"
  // Misc
  lazy val enumeratumVersion = "1.5.13"

  // Test
  lazy val scalaTestVersion = "3.0.5"

  lazy val functionalDependencies = List(
    "org.typelevel" %% "cats-core" % catsVersion,
    "org.typelevel" %% "cats-effect" % catsEffectsVersion
  )

  lazy val jsonDependencies = List(
    "io.circe" %% "circe-core" % circeVersion,
    "io.circe" %% "circe-generic" % circeVersion,
    "io.circe" %% "circe-parser" % circeVersion,
    "io.circe" %% "circe-derivation" % circeDerivationVersion
  )

  lazy val testDependencies = List(
    "org.scalatest" %% "scalatest" % "3.0.5" % Test
  )

  lazy val csvDependencies = List(
    "com.nrinaudo" %% "kantan.csv-enumeratum" % kantanVersion,
    "com.nrinaudo" %% "kantan.csv-cats" % kantanVersion,
    "com.nrinaudo" %% "kantan.csv-generic" % kantanVersion
  )

  lazy val miscDependencies = List(
    "com.beachape" %% "enumeratum" % enumeratumVersion
  )

  lazy val all = functionalDependencies ++ jsonDependencies ++ testDependencies ++ csvDependencies ++ miscDependencies

}
