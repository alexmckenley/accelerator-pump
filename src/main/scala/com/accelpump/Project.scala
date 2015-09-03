package com.accelpump

import spray.json._

case class Project(id: Option[String], name: String, namespace: String)

object ProjectJsonProtocol extends DefaultJsonProtocol {
  implicit val projectFormat = jsonFormat3(Project)
}
