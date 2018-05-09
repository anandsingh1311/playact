package collabsimulations

import SystemPropertiesUtil.{getAsDoubleOrElse, getAsIntOrElse, getAsStringOrElse}
object PerfTestConfig {
  val baseUrl = getAsStringOrElse("baseUrl","https://nqa-chat3.sprinklr.com/spr-chat/rest")
  val mqttUrl = getAsStringOrElse("mqttUrl", "tcp://nqa-chat3.sprinklr.com:1883")
  val firstHitLoad = getAsIntOrElse("firstHitLoad", 40)
  val rampFactorPerSec = getAsIntOrElse("rampFactorPerSec", 15)
  val requestPerSecond = getAsDoubleOrElse("requestPerSecond", 350f)
  val durationMin = getAsIntOrElse("durationMin", 1)
  val meanResponseTimeMs = getAsIntOrElse("meanResponseTimeMs", 100)
  val maxResponseTimeMs = getAsIntOrElse("maxResponseTimeMs", 500)
}
