val Leaf       = "0.1.0"
val Shapeless  = "2.3.3"
val Scala2_11  = "2.11.11"
val Scala2_12  = "2.12.4"
val ScalaTest  = "3.0.4"
val Cats       = "0.9.0"

val SharedSettings = Seq(
  name := "trail",
  organization := "tech.sparse",
  scalaVersion := Scala2_12,
  crossScalaVersions := Seq(Scala2_12, Scala2_11),
  scalacOptions := Seq(
    "-unchecked",
    "-deprecation",
    "-encoding", "utf8"
  ),
  pomExtra :=
    <url>https://github.com/sparsetech/trail</url>
    <licenses>
      <license>
        <name>Apache-2.0</name>
        <url>https://www.apache.org/licenses/LICENSE-2.0.html</url>
      </license>
    </licenses>
    <scm>
      <url>git@github.com:sparsetech/trail.git</url>
    </scm>
    <developers>
      <developer>
        <id>tindzk</id>
        <name>Tim Nieradzik</name>
        <url>http://github.com/tindzk/</url>
      </developer>
    </developers>
)

lazy val root = project.in(file("."))
  .aggregate(js, jvm)
  .settings(SharedSettings: _*)
  .settings(publishArtifact := false)

lazy val trail = crossProject.in(file("."))
  .settings(SharedSettings: _*)
  .settings(
    autoAPIMappings := true,
    apiMappings += (scalaInstance.value.libraryJar -> url(s"http://www.scala-lang.org/api/${scalaVersion.value}/")),
    libraryDependencies ++= Seq(
      "com.chuusai"   %%% "shapeless" % Shapeless,
      "org.typelevel" %%% "cats"      % Cats,
      "org.scalatest" %%% "scalatest" % ScalaTest % "test"
    )
  )
  .jsSettings(
    /* Use io.js for faster compilation of test cases */
    scalaJSStage in Global := FastOptStage
  )

lazy val js  = trail.js
lazy val jvm = trail.jvm

lazy val manual = project.in(file("manual"))
  .dependsOn(jvm)
  .settings(SharedSettings: _*)
  .settings(
    name := "manual",
    publishArtifact := false,
    libraryDependencies += "tech.sparse" %% "leaf-notebook" % Leaf
  )
