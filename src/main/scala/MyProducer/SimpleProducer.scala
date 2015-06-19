package MyProducer

import java.util
import kafka.javaapi.producer.Producer
import kafka.producer.KeyedMessage
import kafka.producer.ProducerConfig

import scala.util.Random

object SimpleProducer {
  def main(args: Array[String]) = {

    val props = new java.util.Properties()
    props.put("metadata.broker.list", "127.0.0.1:9092")

    //props.put
    val config = new ProducerConfig(props)

    val prod = new Producer[Array[Byte], Array[Byte]](config)

    val topic = "test"

    val key = "I am the key"
    val msg = " I am the message"
    val data = new KeyedMessage[Array[Byte], Array[Byte]](topic, key.getBytes, msg.getBytes)

    prod.send(data)
    Thread.sleep(100000)
    prod.close
  }
}
