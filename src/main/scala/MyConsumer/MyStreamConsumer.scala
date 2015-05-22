package MyConsumer

import java.util.Properties

import kafka.consumer.{ConsumerConfig, Consumer}

/**
 * Created by juzhou on 5/22/2015.
 */
object MyStreamConsumer {
  def main (args: Array[String]) {
    val props =  new Properties
    props.put("metadata.broker.list", "localhost:9092")
    val consumerConfig = new ConsumerConfig(props)
    val connector = Consumer.create(consumerConfig)

    // create stream
    val str = connector.createMessageStreams(Map[String, Int]("test", 4))

  }
}
