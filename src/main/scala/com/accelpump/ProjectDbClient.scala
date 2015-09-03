package com.accelpump

import scalikejdbc._
import akka.actor._

class ProjectDbClient(implicit system: ActorSystem) {
  def createProject(project: Project) = {
    DB localTx { implicit session =>
      sql"insert into projects (id, name, namespace) values (DEFAULT, ${project.name}, ${project.namespace})"
        .update
        .apply()
    }
  }

  def getProject(id: Int): Option[Project] = {
    DB.readOnly { implicit session =>
      sql"select * from projects where id=$id"
        .map(Project(_))
        .single
        .apply();
    }
  }

  def getProjects(): List[Project] = {
    DB.readOnly { implicit session =>
      sql"select * from projects"
        .map(Project(_))
        .list
        .apply();
    }
  }
}
