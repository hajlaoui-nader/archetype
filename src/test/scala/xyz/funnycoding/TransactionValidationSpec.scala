package xyz.funnycoding

import cats.data.Validated.{Invalid, Valid}
import org.scalatest.{FunSpec, Matchers}
import xyz.funnycoding.TransactionType.Invoice

class TransactionValidationSpec extends FunSpec with Matchers {

  describe("transaction validation") {
    import Transaction.transactionValidator

    lazy val rawData = List("00097363", "9.76", "invoice", "XYZ Company")
    lazy val rawDataBadId = List("97363", "9.76", "invoice", "XYZ Company")
    lazy val rawDataBadAmount =
      List("00097363", "you cannot parse this !", "invoice", "XYZ Company")
    lazy val rawDataBadTransactionType =
      List("00097363", "9.76", "invoices", "XYZ Company")
    lazy val rawDataBadCompanyName = List("00097363", "9.76", "invoice", "XYZ")

    lazy val rawBadData =
      List("000", "you cannot parse this !", "invoices", "XYZ")

    it("should fail validating id") {
      transactionValidator.validate(rawDataBadId) match {
        case Invalid(e) =>
          assert(e.toNonEmptyList.head == ValidationError("invalid Id"))
        case Valid(v) =>
          fail(s"should fail validating $v")
      }
    }

    it("should fail validating amount") {
      transactionValidator.validate(rawDataBadAmount) match {
        case Invalid(e) =>
          assert(e.toNonEmptyList.head == ValidationError("invalid amount"))
        case Valid(v) =>
          fail(s"should fail validating $v")
      }
    }

    it("should fail validating transaction type") {
      transactionValidator.validate(rawDataBadTransactionType) match {
        case Invalid(e) =>
          assert(
            e.toNonEmptyList.head == ValidationError("invalid transaction type")
          )
        case Valid(v) =>
          fail(s"should fail validating $v")
      }
    }

    it("should fail validating company name") {
      transactionValidator.validate(rawDataBadCompanyName) match {
        case Invalid(e) =>
          assert(
            e.toNonEmptyList.head == ValidationError("invalid company name")
          )
        case Valid(v) =>
          fail(s"should fail validating $v")
      }
    }

    it("should fail validating all fields") {
      transactionValidator.validate(rawBadData) match {
        case Invalid(e) =>
          assert(
            e.toNonEmptyList.toList == List(
              ValidationError("invalid Id"),
              ValidationError("invalid amount"),
              ValidationError("invalid transaction type"),
              ValidationError("invalid company name")
            )
          )
        case Valid(v) =>
          fail(s"should fail validating $v")
      }
    }

    it("should fail validating line format") {
      transactionValidator.validate(List("0", "0", "0", "0", "0", "0")) match {
        case Invalid(e) =>
          assert(e.toNonEmptyList.head == LineFormatError)
        case Valid(v) => fail(s"should fail validating $v")
      }
    }

    it("should validate") {
      transactionValidator.validate(rawData) match {
        case Invalid(e) => fail(e.toNonEmptyList.toList.mkString(","))
        case Valid(v) =>
          assert(v.id == "00097363")
          assert(v.amount == 9.76f)
          assert(v.transactionType == Invoice)
          assert(v.companyName == "XYZ Company")
      }
    }
  }
}
