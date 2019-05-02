package xyz.funnycoding
import akka.actor.ActorSystem
import akka.http.scaladsl.Http
import akka.http.scaladsl.Http.ServerBinding
import xyz.funnycoding.config.Config
import xyz.funnycoding.routes.AllRoutes

import scala.concurrent.duration._
import scala.concurrent.{Await, Future}
import scala.language.postfixOps

object Main extends App {

  implicit val config: Config = new Config()
  import config._

  val server = Await.result(startServer, 5 seconds)

  sys.addShutdownHook {
    Await.ready(stopServer(server), 5 seconds)
  }

  def stopServer(
      server: ServerBinding
  )(implicit s: ActorSystem): Future[Unit] = {
    for {
      _ <- server.unbind()
      _ <- s.terminate()
    } yield ()
  }

  def startServer: Future[Http.ServerBinding] = {
    Http().bindAndHandle(AllRoutes.routes(), "0.0.0.0", port)
  }
}
