package xyz.funnycoding
import xyz.funnycoding.service.DumpService

object Main extends App {

  DumpService[Transaction]
    .streamJson("/transactions-dump.csv")
    .foreach(println)
}
