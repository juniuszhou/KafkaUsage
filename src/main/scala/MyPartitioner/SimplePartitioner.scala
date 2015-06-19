package MyPartitioner

import kafka.javaapi.producer.Producer
import kafka.producer.{DefaultPartitioner, KeyedMessage, ProducerConfig}

object SimplePartitioner {
  def main(args: Array[String]) = {

    val props = new java.util.Properties()
    props.put("metadata.broker.list", "localhost:9092")
    val par = new DefaultPartitioner
    props.put("partitioner.class", "kafka.producer.DefaultPartitioner")
    //config.partitionerClass
    val config = new ProducerConfig(props)

    val prod = new Producer[String, String](config)

    val topic = "test"
    val key = "127.0.0.1"
    val msg = " I am the message"
    val data = new KeyedMessage[String, String](topic, key, msg)

    prod.send(data)
    prod.close
  }
}
