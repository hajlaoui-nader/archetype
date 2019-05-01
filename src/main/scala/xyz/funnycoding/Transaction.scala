package xyz.funnycoding

import cats.data.{Validated, ValidatedNec}
import cats.implicits._
import enumeratum._
import io.circe.{Encoder, Json}
import kantan.csv.ops._
import kantan.csv.rfc
import xyz.funnycoding.TransactionType.{Deposit, Invoice, Payment, Withdrawal}
import xyz.funnycoding.service.{CsvService, DumpService}

import scala.collection.immutable
import scala.util.Try

final case class Transaction(
    id: String,
    amount: Float,
    transactionType: TransactionType,
    companyName: String
)

object Transaction {

  implicit val transactionValidator: Validator[Transaction] =
    new Validator[Transaction] {
      override def validate(
          raw: List[String]
      ): ValidationResult[Transaction] = {
        raw match {
          case id :: amount :: transactionType :: companyName :: Nil =>
            (
              validateId(id),
              validateAmount(amount),
              validateTransactionType(transactionType),
              validateCompanyName(companyName)
            ).mapN(Transaction.apply)
          case _ => LineFormatError.invalidNec
        }
      }

      def validateId(id: String): ValidationResult[String] = {
        id match {
          case value if value.length > 5 => value.validNec
          case _                         => ValidationError("invalid Id").invalidNec
        }
      }

      def validateAmount(amout: String): ValidationResult[Float] = {
        Validated
          .fromTry(Try(amout.toFloat))
          .leftMap(_ => ValidationError("invalid amount"))
          .toValidatedNec
      }

      def validateTransactionType(
          value: String
      ): ValidationResult[TransactionType] = {
        value match {
          case "deposit"    => Validated.valid(Deposit)
          case "payment"    => Validated.valid(Payment)
          case "withdrawal" => Withdrawal.validNec
          case "invoice"    => Invoice.validNec
          case _            => ValidationError("invalid transaction type").invalidNec
        }
      }

      def validateCompanyName(
          companyName: String
      ): ValidatedNec[ValidatorError, String] = {
        companyName match {
          case value if value.length > 5 => value.validNec
          case _                         => ValidationError("invalid company name").invalidNec
        }
      }
    }

  implicit val transactionCsvService: CsvService[Transaction] =
    (filePath: String) => {
      getClass
        .getResource(filePath)
        .asCsvReader[List[String]](rfc.withHeader)
        .toStream
    }

  implicit val transactionEncoder: Encoder[Transaction] =
    (t: Transaction) =>
      Json.obj(
        ("id", Json.fromString(t.id)),
        (
          "amount",
          Json
            .fromFloat(t.amount)
            .getOrElse(Json.fromString("failed to parse to json"))
        ),
        ("transaction type", Json.fromString(t.transactionType.entryName)),
        ("company name", Json.fromString(t.companyName))
      )

  implicit val transactionDumpService: DumpService[Transaction] =
    (filePath: String) => transactionCsvService.streamRaw(filePath)

}

sealed abstract class TransactionType(override val entryName: String)
    extends EnumEntry

object TransactionType extends Enum[TransactionType] {

  override def values: immutable.IndexedSeq[TransactionType] = findValues

  case object Invoice extends TransactionType("invoice")
  case object Withdrawal extends TransactionType("withdrawal")
  case object Deposit extends TransactionType("deposit")
  case object Payment extends TransactionType("payment")
}
