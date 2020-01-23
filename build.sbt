import sbtorgpolicies.utils.getEnvVar
import wartremover.Wart

Global / onChangedBuildSource := ReloadOnSourceChanges

/* dependency versions */
lazy val V = new {
  val algebird = "0.13.6"
  val cats = "2.0.0"
  val kindProjector = "0.10.3"
  val rainier = "0.2.3"
  val scala = "2.12.10"
  val scalacheck = "1.14.3"
  val scalatest = "3.1.0"
  val scalaTestPlus = "3.1.0.0"
  val util = "19.12.0"
}

val compilerOptions = Seq(
  "-unchecked",
  "-deprecation",
  "-Xlint",
  "-language:implicitConversions",
  "-language:higherKinds",
  "-language:existentials"
)

// The console can't handle these.
val consoleExclusions = Seq(
  "-Ywarn-unused:imports", "-Xfatal-warnings", "-Xlint"
)

val ignoredWarts: Set[Wart] =
  Set(Wart.DefaultArguments, Wart.TraversableOps, Wart.Any, Wart.NonUnitStatements)

def unsafeWartsExcept(ws: Set[wartremover.Wart]): Seq[wartremover.Wart] =
  Warts.unsafe.filterNot(w => ws.exists(_.clazz == w.clazz))

val sharedSettings = Seq(
  organization := "io.samritchie",
  scalaVersion := V.scala,

  // Lets me C-c out of the running process.
  cancelable in Global := true,
  parallelExecution in Test := true,
  scalafmtOnCompile in ThisBuild := true,
  wartremoverErrors in (ThisBuild, compile) ++= unsafeWartsExcept(ignoredWarts),
  resolvers ++= Seq(
    Resolver.bintrayRepo("cibotech", "public")
  ),
  scalacOptions ++= Seq(
    "-unchecked",
    "-deprecation",
    "-Xlint",
    "-language:implicitConversions",
    "-language:higherKinds",
    "-language:existentials"),


  scalacOptions in (Compile, console) --= consoleExclusions,
  scalacOptions in (Test, console) --= consoleExclusions
)

lazy val blog = Project(
  id = "blog",
  base = file("."))
  .settings(sharedSettings)
  .settings(
    libraryDependencies ++= Seq(
     "com.stripe" %% "rainier-cats" % V.rainier,
     "com.stripe" %% "rainier-core" % V.rainier,

    // For the monoids and implementations.
    "com.twitter" %% "algebird-core" % V.algebird,
     "com.twitter" %% "util-core" % V.util,

    // For its typeclasses, Monad specifically.
    "org.typelevel" %% "cats-core" % V.cats,
     "org.typelevel" %% "cats-free" % V.cats,

    // Testing.
    "com.twitter" %% "algebird-test" % V.algebird % Test,
    "org.scalatest" %% "scalatest" % V.scalatest % Test,
    "org.scalacheck" %% "scalacheck" % V.scalacheck % Test,
    "org.scalatestplus" %% "scalacheck-1-14" % V.scalaTestPlus % Test
    ) ++ Seq(compilerPlugin("org.typelevel" %% "kind-projector" % V.kindProjector))
  )
