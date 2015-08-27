package com.accelpump

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent.duration._
import scalikejdbc._
import scalikejdbc.config._

object Boot extends App {

  implicit val system = ActorSystem("accelerator-pump")

  // create and start the ApiServiceActor
  val apiService = system.actorOf(Props[ApiServiceActor], "api-service")

  implicit val timeout = Timeout(5.seconds)

  // Move this to a better place
  DBs.setupAll()

  val projects = DB readOnly { implicit session =>
    sql"select * from projects".map(rs => (rs.string("name"), rs.long("id"))).list.apply()
  }

  println(projects)

  // start the HTTP server
  IO(Http) ? Http.Bind(apiService, interface = "localhost", port = 8080)
}
