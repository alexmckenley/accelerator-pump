package com.accelpump

import akka.actor.{ActorSystem, Props}
import akka.io.IO
import spray.can.Http
import akka.pattern.ask
import akka.util.Timeout
import scala.concurrent._
import scala.concurrent.duration._
import scalikejdbc.config._

object Boot extends App {
  implicit val ec = AcceleratorPump.system.dispatcher

  AcceleratorPump.initialize()
  AcceleratorPump.system.log.info("Booting AcceleratorPump...")

  AcceleratorPump.startHttpServer().onFailure {
    case ex:Throwable =>
      AcceleratorPump.system.log.error(ex, "Error starting HTTP server, exiting...")
      AcceleratorPump.system.shutdown()
  }(AcceleratorPump.system.dispatcher)

  val projectDb = new ProjectDbClient()(AcceleratorPump.system)

  for {
    newProject <- projectDb.createProject(Project(None, "alex", "talorr"))
    allProjects <- projectDb.getProjects()
  } yield println(newProject, allProjects)
}

object AcceleratorPump {
  implicit val system = ActorSystem("accelerator-pump")
  implicit val timeout = Timeout(5.seconds)

  def initialize():Unit = {
    startDbConnection()
  }

  def startDbConnection(): Unit = {
    DBs.setupAll()
    system.registerOnTermination {
      system.log.info("Shutting down MySQL connection pool...")
      DBs.closeAll()
    }
  }

  def startHttpServer(): Future[Unit] = {
    import system.dispatcher
    val apiService = AcceleratorPump.system.actorOf(Props[ApiServiceActor], "api-service")
    (IO(Http) ? Http.Bind(apiService, interface = "localhost", port = 8008)).map(_ => Unit)
  }
}
