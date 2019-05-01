package xyz.funnycoding.service

import cats.data.Validated.{Invalid, Valid}
import cats.implicits._
import kantan.csv.ParseError.IOError
import org.scalatest.{FunSpec, Matchers}
import xyz.funnycoding.{Transaction, ValidationError, Validator}

class TransactionCsvServiceSpec extends FunSpec with Matchers {

  val failValidator: Validator[Transaction] = (_: List[String]) =>
    ValidationError("fake error").invalidNec

  describe("transaction csv service ") {
    lazy val service: CsvService[Transaction] =
      Transaction.transactionCsvService

    it("should fail loading file") {
      service.streamRaw("sorry its invalid )-___-(").toList.head match {
        case Left(e)  => assert(e.isInstanceOf[IOError])
        case Right(v) => fail(s"should fail ! $v")
      }
    }

    it("should load file") {
      service.streamRaw("/transactions-dump.csv").toList.head match {
        case Left(e)  => fail(s"should not fail $e")
        case Right(v) => assert(v.nonEmpty)
      }
    }

    it("should fail stream validated on IO error") {
      service.streamValidated("/sorry its invalid )-___-(") match {
        case Stream.Empty => succeed
        case _            => fail(" stream is not empty on io error")
      }
    }

    it("should stream validated") {
      service.streamValidated("/transactions-dump.csv") match {
        case Stream.Empty => fail("stream is empty !")
        case _            => succeed
      }
    }

    it("should load file and fail validation") {
      service
        .streamValidated("/transactions-dump.csv")(failValidator)
        .head match {
        case Valid(_)   => fail("should fail validation")
        case Invalid(_) => succeed
      }
    }

    it("should fail stream only valid transactions") {
      service.streamValid("/transactions-dump.csv")(failValidator) match {
        case Stream.Empty => succeed
        case _ #:: _      => fail("stream is not empty")
      }
    }

    it("should stream only valid transactions") {
      service.streamValid("/transactions-dump.csv") match {
        case Stream.Empty => fail("stream is empty")
        case _ #:: _      => succeed
      }
    }
  }

}
