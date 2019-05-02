package xyz.funnycoding.config

import akka.actor.ActorSystem
import akka.stream.ActorMaterializer

import scala.concurrent.ExecutionContextExecutor

class Config {
  val port = 8090

  implicit val actorSystem: ActorSystem = ActorSystem(
    "transactions-dump-system"
  )
  implicit val actorMaterializer: ActorMaterializer = ActorMaterializer()
  implicit val ec: ExecutionContextExecutor = actorSystem.dispatcher
}
