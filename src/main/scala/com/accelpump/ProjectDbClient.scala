package com.accelpump

import scalikejdbc._
import org.hashids._
import scala.concurrent._
import scala.concurrent.duration._
import akka.actor._

class ProjectDbClient(implicit system: ActorSystem) {
  implicit val ec = system.dispatcher

  val hashids = Hashids("Like leaves, falling from trees...")

  def mapProjects(rs: WrappedResultSet): Project = {
    Project(Some(hashids.encode(rs.int("id"))), rs.string("name"), rs.string("namespace"))
  }

  def createProject(project: Project): Future[Option[Project]] = Future {
    blocking {
      project.id match {
        case Some(_) => None
        case None =>  {
          val id = DB localTx { implicit session =>
            sql"insert into projects (name, namespace) values (${project.name}, ${project.namespace})"
              .updateAndReturnGeneratedKey
              .apply()
          }

          Await.result(getProject(hashids.encode(id)), 5 seconds)
        }
      }
    }
  }

  def getProject(id: String): Future[Option[Project]] = Future {
    blocking {
      DB.readOnly { implicit session =>
        sql"select * from projects where id=${hashids.decode(id)}"
          .map(mapProjects)
          .single
          .apply();
      }
    }
  }

  def getProjects(): Future[List[Project]] = Future {
    blocking {
      DB.readOnly { implicit session =>
        sql"select * from projects"
          .map(mapProjects)
          .list
          .apply();
      }
    }
  }
}
