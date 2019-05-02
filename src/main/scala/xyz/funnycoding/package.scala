package xyz

import cats.data.ValidatedNec

package object funnycoding {
  type ValidationResult[A] = ValidatedNec[ValidatorError, A]
}
