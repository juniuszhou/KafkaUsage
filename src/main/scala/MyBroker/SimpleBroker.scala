package MyBroker

import kafka.consumer.{ConsumerConfig, Consumer}
import kafka.producer.ProducerConfig

/**
 * Created by juzhou on 4/27/2015.
 */
object SimpleBroker {
  def main(args: Array[String]) {
    val props = new java.util.Properties()
    props.put("metadata.broker.list", "localhost:8888")
    props.put("serializer.class", "kafka.serializer.StringEncoder")
    props.put("zookeeper.connect", "localhost:9999")
    //props.put("zookeeper.connect", null)
    props.put("request.required.acks", "1")
    props.put("group.id", "1")
    val config = new ConsumerConfig(props)

    // create consumer connector via config
    val consumerCon = Consumer.create(config)

    // create stream
    val consumerStream = consumerCon.createMessageStreams(Map[String,Int]("topic one" -> 1))

    consumerStream.get("topic one") match {
      case Some(streams) => println(streams)
      case _ => println("nothing")
    }

  }
}
