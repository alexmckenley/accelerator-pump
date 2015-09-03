package com.accelpump

import scalikejdbc._
import spray.json._

case class Project(id: Int, name: String, namespace: String)

object Project {
  def apply(rs: WrappedResultSet) = new Project(id = rs.int("id"), name = rs.string("name"), namespace = rs.string("namespace"))
}

object ProjectJsonProtocol extends DefaultJsonProtocol {
  implicit val projectFormat = jsonFormat3(Project.apply)
}
