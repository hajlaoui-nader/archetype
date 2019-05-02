package xyz.funnycoding.routes

import akka.NotUsed
import akka.http.scaladsl.server.Directives._
import akka.http.scaladsl.server.Route
import akka.stream.scaladsl._
import xyz.funnycoding.Transaction
import xyz.funnycoding.service.DumpService
import CirceSupport._
import akka.http.scaladsl.common.EntityStreamingSupport
import akka.util.ByteString
import io.circe.generic.auto._

object TransactionDumpRoute {

  val start: ByteString = ByteString.empty
  val sep: ByteString = ByteString("\n")
  val end: ByteString = ByteString.empty

  implicit val ess: EntityStreamingSupport = EntityStreamingSupport
    .json()
    .withFramingRenderer(Flow[ByteString].intersperse(start, sep, end))

  val transactionDumpSource: Source[Transaction, NotUsed] = Source.fromIterator(
    () =>
      DumpService[Transaction]
        .streamValid("/transactions-dump.csv")
        .iterator
  )

  lazy val transactionDumpList: List[Transaction] = DumpService[Transaction]
    .streamValid("/transactions-dump.csv")
    .toList

  def routes(): Route = {
    path("td") {
      parameter('transactionChunk.?) { transactionChunk =>
        complete(
          transactionDumpList
            .filter(_.id.contains(transactionChunk.getOrElse("")))
        )
      }
    } ~ path("tdstream") {
      parameter('transactionChunk.?) { transactionChunk =>
        complete(
          transactionDumpSource
            .filter(_.id.contains(transactionChunk.getOrElse("")))
        )
      }
    }

  }

}
