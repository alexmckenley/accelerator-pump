package com.accelpump

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._

object Boot extends App {

  implicit val system = ActorSystem("accelerator-pump")

  // create and start our service actor
  val apiService = system.actorOf(Props[ApiServiceActor], "api-service")

  implicit val timeout = Timeout(5.seconds)

  // start the HTTP server
  IO(Http) ? Http.Bind(apiService, interface = "localhost", port = 8080)
}
