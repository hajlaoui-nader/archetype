package xyz.funnycoding.service

import io.circe.Json
import org.scalatest.{FunSpec, Matchers}
import xyz.funnycoding.Transaction

class TransactionDumpServiceSpec extends FunSpec with Matchers {
  lazy val rawData = List("00097363", "9.76", "invoice", "XYZ Company")
  describe("transaction dump service") {

    it("should stream to json") {
      implicit val stringTransactionDumpService: DumpService[Transaction] =
        (_: String) => Stream(Right(rawData))

      stringTransactionDumpService
        .streamJson("/transactions-dump.csv")
        .toList
        .head match {
        case _: Json => succeed
        case _       => fail
      }
    }
  }

}
