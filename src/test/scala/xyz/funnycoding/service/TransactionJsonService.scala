package xyz.funnycoding.service

import io.circe.syntax._
import org.scalatest.{FunSpec, Matchers}
import xyz.funnycoding.Transaction
import xyz.funnycoding.TransactionType.Invoice

class TransactionJsonService extends FunSpec with Matchers {

  describe("transaction json service") {
    it("should encode transaction in json format") {
      assert(
        Transaction("090907", 9.76f, Invoice, "XYZ Company").asJson.noSpaces
          == s"""{"id":"090907","amount":9.76,"transaction type":"invoice","company name":"XYZ Company"}"""
      )
    }
  }

}
