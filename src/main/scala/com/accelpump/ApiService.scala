package com.accelpump

import com.accelpump.ProjectJsonProtocol._
import akka.actor.Actor
import spray.httpx.SprayJsonSupport._
import spray.routing._

class ApiServiceActor extends Actor with ApiService {

  def actorRefFactory = context

  def receive = runRoute(apiRoutes)
}

trait ApiService extends HttpService {

  val apiRoutes =
    path("") {
      get {
        complete(Project(1, "ammo", "ammo"))
      }
    }
}
