package xyz.funnycoding.routes

import akka.http.scaladsl.server.Route

object AllRoutes {

  def routes(): Route = TransactionDumpRoute.routes()

}
