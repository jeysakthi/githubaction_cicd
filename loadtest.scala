package simulations

import io.gatling.core.scenario.Simulation
import io.gatling.core.Predef._
import io.gatling.http.Predef._

class TestAPIsimulation extends Simulation{

  val httpconf = http.baseUrl("https://gorest.co.in/")
    .header("Authorization", "Bearer 005606fc765b07c5b3e5d46ae808fb0390c28d6322a216f1fd42b111a1afb17e")

  val scn = scenario("check correlation and extract data")
    .exec(http("Get all users")
      .get("public/v2/users")
      .check(jsonPath("$..id").saveAs("userId")))

    .exec(http("Get specific user")
      .get("public/v2/users/${userId}")
      .check(jsonPath("$..id").is("3306"))
      //    .check(jsonPath("$..email").is("kanchan_pillai@greenfelder-ferry.biz"))
    )

  //  setUp(scn.inject(atOnceUsers(1))).protocols(httpconf)
  setUp(
    scn.inject(nothingFor(5),
      atOnceUsers(5),
      rampUsers(10) during (10))).protocols(httpconf)

}
