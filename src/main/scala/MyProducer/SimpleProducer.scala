package MyProducer

import java.util
import kafka.javaapi.producer.Producer
import kafka.producer.KeyedMessage
import kafka.producer.ProducerConfig

import scala.util.Random

object SimpleProducer {
  def main(args: Array[String]) = {

    val props = new java.util.Properties()
    props.put("metadata.broker.list", "localhost:9092")
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
