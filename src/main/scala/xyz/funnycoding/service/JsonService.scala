package xyz.funnycoding.service
import io.circe.{Encoder, Json}
import io.circe.syntax._

trait JsonService[A] {
  def toJson(a: A)(implicit e: Encoder[A]): Json = a.asJson
}
object JsonService {
  def apply[A](implicit js: JsonService[A]): JsonService[A] = js
}
