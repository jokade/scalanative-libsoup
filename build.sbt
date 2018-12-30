organization in ThisBuild := "de.surfice"

version in ThisBuild := "0.0.1-SNAPSHOT"

scalaVersion in ThisBuild := "2.11.12"

val Version = new {
  val gtk         = "0.0.2-SNAPSHOT"
  val obj_interop = "0.0.6-SNAPSHOT"
  //val slogging    = "0.5.3"
  val smacrotools = "0.0.8"
  val utest       = "0.6.3"
}


lazy val commonSettings = Seq(
  scalacOptions ++= Seq("-deprecation","-unchecked","-feature","-language:implicitConversions","-Xlint"),
  addCompilerPlugin("org.scalamacros" % "paradise" % "2.1.0" cross CrossVersion.full),
  libraryDependencies ++= Seq(
    "de.surfice" %%% "scalanative-interop-cobj" % Version.obj_interop,
    "de.surfice" %%% "scalanative-glib" % Version.gtk,
    "de.surfice" %%% "scalanative-json-glib" % Version.gtk,
    "de.surfice" %%% "scalanative-gio"  % Version.gtk
    //"com.lihaoyi" %%% "utest" % Version.utest % "test"
    ),
    testFrameworks += new TestFramework("utest.runner.Framework")
  )

lazy val libsoup = project.in(file("."))
  .enablePlugins(ScalaNativePlugin,NBHPkgConfigPlugin)
  .settings(commonSettings ++ publishingSettings:_*)
  .settings(
    name := "scalanative-libsoup",
    description := "Object-oriented bindings to libsoup for Scala Native",
    nbhPkgConfigModules += "libsoup-2.4",
    nativeLinkStubs := true,
    nativeLinkingOptions ++= nbhNativeLinkingOptions.value
    )

lazy val dontPublish = Seq(
  publish := {},
  publishLocal := {},
  com.typesafe.sbt.pgp.PgpKeys.publishSigned := {},
  com.typesafe.sbt.pgp.PgpKeys.publishLocalSigned := {},
  publishArtifact := false,
  publishTo := Some(Resolver.file("Unused transient repository",file("target/unusedrepo")))
)

lazy val publishingSettings = Seq(
  publishMavenStyle := true,
  publishTo := {
    val nexus = "https://oss.sonatype.org/"
    if (isSnapshot.value)
      Some("snapshots" at nexus + "content/repositories/snapshots")
    else
      Some("releases"  at nexus + "service/local/staging/deploy/maven2")
  },
  pomExtra := (
    <url>https://github.com/jokade/scalanative-libsoup</url>
    <licenses>
      <license>
        <name>MIT License</name>
        <url>http://www.opensource.org/licenses/mit-license.php</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:jokade/scalanative-libsoup</url>
      <connection>scm:git:git@github.com:jokade/scalanative-libsoup.git</connection>
    </scm>
    <developers>
      <developer>
        <id>jokade</id>
        <name>Johannes Kastner</name>
        <email>jokade@karchedon.de</email>
      </developer>
    </developers>
  )
)
 
