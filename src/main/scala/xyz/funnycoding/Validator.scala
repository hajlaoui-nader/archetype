package xyz.funnycoding

sealed trait ValidatorError
final case class ValidationError(error: String) extends ValidatorError
case object LineFormatError extends ValidatorError

trait Validator[A] {
  def validate(raw: List[String]): ValidationResult[A]

}
object Validator {
  def apply[A](raw: List[String])(
      implicit v: Validator[A]
  ): ValidationResult[A] = v.validate(raw)
}
