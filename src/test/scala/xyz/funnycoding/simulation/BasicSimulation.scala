package xyz.funnycoding.simulation

import io.gatling.core.Predef._
import io.gatling.http.Predef._

class BasicSimulation extends Simulation {
  val httpProtocol = http
    .baseUrl("http://127.0.0.:8090/tdstream") // Here is the root for all relative URLs
    .doNotTrackHeader("1")

  val scn =
    scenario("stream result") // A scenario is a chain of requests and pauses
      .exec(
        http("request_1")
          .get("/")
      )
      .pause(7) // Note that Gatling has recorder real time pauses

  setUp(scn.inject(atOnceUsers(1)).protocols(httpProtocol))

}
