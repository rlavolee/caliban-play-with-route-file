name := "caliban-play-with-route-file"
 
version := "1.0" 
      
lazy val `caliban-play-with-route-file` = (project in file(".")).enablePlugins(PlayScala)
      
resolvers += "Akka Snapshot Repository" at "https://repo.akka.io/snapshots/"
      
scalaVersion := "2.13.5"

libraryDependencies ++= Seq(
  guice,
  "com.github.ghostdogpr" %% "caliban-play"      % "1.1.1"
)
