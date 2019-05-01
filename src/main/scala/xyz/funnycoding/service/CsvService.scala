package xyz.funnycoding.service

import kantan.csv.ReadResult
import xyz.funnycoding.{ValidationResult, Validator}

trait CsvService[A] {
  def streamRaw(filePath: String): Stream[ReadResult[List[String]]]

  private def validate(raw: List[String])(
      implicit v: Validator[A]
  ): ValidationResult[A] = v.validate(raw)

  def streamValidated(
      filePath: String
  )(implicit v: Validator[A]): Stream[ValidationResult[A]] = {
    streamRaw(filePath)
      .filter(_.isRight)
      .map(_.right.get)
      .map(validate)
  }

  def streamValid(filePath: String)(implicit v: Validator[A]): Stream[A] = {
    streamValidated(filePath)
      .filter(_.isValid)
      .map(_.toEither.right.get)
  }

}

object CsvService {
  def apply[A](implicit c: CsvService[A]): CsvService[A] = c
}
