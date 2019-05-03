package xyz.funnycoding.tagless

import cats.Monad
import cats.effect.{IO, Sync}
import cats.syntax.functor._, cats.syntax.flatMap._

trait Console[F[_]] {
  def putStrLn(line: String): F[Unit]
  def getStrLn: F[String]
}
object Console {
  def apply[F[_]](implicit F: Console[F]): Console[F] = F
}
class LiveConsole[F[_]: Sync] extends Console[F] {
  def putStrLn(line: String): F[Unit] =
    Sync[F].delay(println(line))
  def getStrLn: F[String] =
    Sync[F].delay(scala.io.StdIn.readLine())
}

object Main {
  def run[F[_]: Monad: Console](): F[String] = {
    for {
      _ <- Console[F].putStrLn("Good morning, what’s your name?")
      name <- Console[F].getStrLn
      _ <- Console[F].putStrLn(s"Great to meet you, $name")
    } yield name
  }
}

object test extends App {

  /*implicit val ioConsole: Console[IO] = new LiveConsole[IO]
  Main.run[IO]().unsafeRunSync()*/

  Sync[IO].delay(dd).attempt.unsafeRunSync() match {
    case Left(e)  => println(s"tnekit chabeb $e")
    case Right(v) => println(s"tnekit chabeb $v")
  }

  private def dd() = {
    if (1 == 1) {
      throw new RuntimeException

    } else ()
  }
}
