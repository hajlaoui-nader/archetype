package xyz.funnycoding.service

import io.circe.{Encoder, Json}
import xyz.funnycoding.Validator

trait DumpService[A] extends CsvService[A] with JsonService[A] {
  def streamJson(
      filePath: String
  )(implicit v: Validator[A], e: Encoder[A]): Stream[Json] =
    streamValid(filePath).map(toJson)
}

object DumpService {
  def apply[A](implicit ds: DumpService[A]): DumpService[A] = ds

}
