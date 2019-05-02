package xyz.funnycoding.routes

import akka.http.scaladsl.model.StatusCodes
import akka.http.scaladsl.testkit.ScalatestRouteTest
import org.scalatest.{FunSpec, Matchers}

class TransactionDumpRouteSpec
    extends FunSpec
    with Matchers
    with ScalatestRouteTest {

  describe("transaction dump route") {
    it("should stream transactions dump") {
      Get("/td") ~> TransactionDumpRoute.routes() ~> check {
        assert(responseAs[String].contains("NL31AUZG2080900107"))
        status shouldEqual StatusCodes.OK
      }
    }
  }

}
