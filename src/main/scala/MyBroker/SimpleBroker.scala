
package MyBroker

import kafka.Kafka

object SimpleBroker{
  def main (args: Array[String]) {
    // to start kafka broker. you need call multiply times if multiply brokers.

    /*
    broker.id=1
    port=9093
    log.dir=/tmp/kafka-logs-1
     */
    Kafka.main(Array[String]())
  }
}

