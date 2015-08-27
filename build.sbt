organization  := "com.accelpump"

version       := "0.1.0"

scalaVersion  := "2.11.7"

scalacOptions := Seq("-unchecked", "-deprecation", "-encoding", "utf8")

libraryDependencies ++= {
  val scalikejdbcV = "2.2.8"
  val akkaV = "2.3.9"
  val sprayV = "1.3.3"

  Seq(
    "org.scalikejdbc"     %%  "scalikejdbc"           % scalikejdbcV,
    "org.scalikejdbc"     %%  "scalikejdbc-config"    % scalikejdbcV,
    "org.scalikejdbc"     %%  "scalikejdbc-test"      % scalikejdbcV,
    "mysql"               %   "mysql-connector-java"  % "5.1.12",
    "ch.qos.logback"      %   "logback-classic"       % "1.1.3",
    "io.spray"            %%  "spray-can"             % sprayV,
    "io.spray"            %%  "spray-routing"         % sprayV,
    "io.spray"            %%  "spray-testkit"         % sprayV  % "test",
    "io.spray"            %%  "spray-json"            % "1.3.2",
    "com.typesafe.akka"   %%  "akka-actor"            % akkaV,
    "com.typesafe.akka"   %%  "akka-testkit"          % akkaV   % "test",
    "org.specs2"          %%  "specs2-core"           % "2.3.11" % "test"
  )
}

Revolver.settings
