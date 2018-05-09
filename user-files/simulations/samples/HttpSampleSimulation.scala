package collabsimulations

import io.gatling.core.Predef._
import io.gatling.core.action.builder.FeedBuilder
import io.gatling.core.structure.{PopulationBuilder, ScenarioBuilder}
import io.gatling.http.Predef._

import PerfTestConfig.{baseUrl, durationMin, maxResponseTimeMs, meanResponseTimeMs}
import scala.concurrent.duration._

class ChatSummarySimulation extends Simulation {

  val httpConf = http
    .baseURL(baseUrl).header("Content-Type", "application/json")
    .header("Accept-Encoding", "gzip")
    .header("Accept", "application/json, text/plain, */*")

  val groupParticipantFeeder = csv("Groups.csv").random.circular

  val chatsummary = scenario("chat-summary-rest")
    .feed(groupParticipantFeeder)
    .exec(http("chat-summary")
      .post("/publish/command/5/59637151e4b06aec030f4ebb/${UserId}")
      .body(StringBody("""{"dtoType":"REQUEST","locale":"en","trackingId":"5","senderId":"${UserId}","sortDirection":"desc","start":0,"rows":30,"untilDate":1523010925072,"convSortOrder":"timestamp","commandType":"CHAT_SUMMARY"}"""))
    )

  setUp(chatsummary.inject(
    atOnceUsers(0),
    rampUsers(100) over (1 seconds),
    constantUsersPerSec(500) during (durationMin minutes))
    .protocols(httpConf))
    .assertions(
      global.responseTime.max.lt(meanResponseTimeMs),
      global.responseTime.mean.lt(maxResponseTimeMs),
      global.successfulRequests.percent.gt(95)
    )
}
