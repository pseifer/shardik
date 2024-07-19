import NativePackagerHelper._

val scala3Version = "3.3.1"

lazy val shar = RootProject(uri("https://github.com/pseifer/shar.git"))

// Native Packager plugin.
enablePlugins(JavaAppPackaging)

lazy val root = project
  .in(file("."))
  .dependsOn(shar)
  .settings(
    // Project metadata.
    name := "shardik",
    maintainer := "github@seifer.me",
    organization := "de.pseifer",
    version := "0.1.1",
    // Project settings.
    run / fork := true,
    run / outputStrategy := Some(StdoutOutput),
    run / javaOptions += "-Xmx4G",
    run / javaOptions += "-Dfile.encoding=UTF-8",
    scalaVersion := scala3Version,
    scalacOptions ++= Seq("-unchecked", "-deprecation", "-feature"),
    // Settings for native packer.
    Compile / mainClass := Some("de.pseifer.shardik.main"),
    Compile / discoveredMainClasses := Seq(),
    Universal / mappings += file("README.md") -> "README.md",
    Universal / packageName := "shar",
    // Logging.// Dependencies.
    // Development dependency, local only - install manually and comment out 'dependsOn(shar)'.
    // Note: This is only needed so metals works correctly with the GitHub dependency for SHAR.
    //libraryDependencies += "de.pseifer" %% "shar" % "1.0.0",
    // CLI application.
    libraryDependencies += "org.rogach" %% "scallop" % "4.1.0",
    // Logging.
    libraryDependencies += "org.slf4j" % "slf4j-simple" % "2.0.12" % Runtime,
    // Testing
    libraryDependencies += "org.scalameta" %% "munit" % "0.7.29" % Test
  )
