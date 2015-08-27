package com.accelpump

import spray.json._

case class Project(id: Int, name: String, namespace: String)

object ProjectJsonProtocol extends DefaultJsonProtocol {
  implicit val projectFormat = jsonFormat3(Project)
}
